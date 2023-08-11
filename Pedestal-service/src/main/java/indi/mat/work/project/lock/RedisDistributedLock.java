package indi.mat.work.project.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author : lbl
 * @Email : libinglin@cetcbigdata.com
 * @Create : 2022/11/11 16:07
 * @Description : 基于LUA脚本的分布式锁
 */

@Component
public class RedisDistributedLock {

    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);

    /**
     * 默认超时时间
     */
    long TIMEOUT_MILLIS = 5000L;

    /**
     * 重试次数
     */
    int RETRY_TIMES = 100;

    /**
     * 每次重试后等待的时间
     */
    long SLEEP_MILLIS = 500L;

    private RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private static final String UNLOCK_LUA;

    /*
     * 通过lua脚本释放锁,来达到释放锁的原子操作
     */
    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then " + "    return redis.call(\"del\",KEYS[1]) "
                + "else " + "    return 0 " + "end ";
    }

    /**
     * 默认锁
     *
     * @param key
     * @return
     */
    public boolean lock(String key) {
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }


    public boolean hasKey(String key){
        redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate.hasKey(key);
    }


    public void setValue(String key, int value){
        redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.opsForValue().set(key, value);
    }


    public Integer increment(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Long v = valueOperations.increment(key, 1);
        return v.intValue();
    }

    /**
     * 获取锁
     *
     * @param key
     *            锁的key
     * @param expire
     *            获取锁超时时间
     * @param retryTimes
     *            重试次数
     * @param sleepMillis
     *            获取锁失败的重试间隔
     * @return 成功/失败
     */
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                log.debug("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    private boolean setRedis(final String key, final long expire) {
        try {
            redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
            boolean status = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] uuidByte = redisTemplate.getStringSerializer().serialize(uuid);
                boolean result = connection.set(keyByte, uuidByte, Expiration.from(expire, TimeUnit.MILLISECONDS),
                        RedisStringCommands.SetOption.ifAbsent());
                return result;
            });
            return status;
        } catch (Exception e) {
            log.error("set redisDistributeLock occured an exception", e);
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key
     *            锁的key
     * @return 成功/失败
     */
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
            Boolean result = redisTemplate.execute((RedisCallback<Boolean>)connection -> {
                byte[] scriptByte = redisTemplate.getStringSerializer().serialize(UNLOCK_LUA);
                return connection.eval(scriptByte, ReturnType.BOOLEAN, 1,
                        redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(lockFlag.get()));
            });
            return result;
        } catch (Exception e) {
            log.error("release redisDistributeLock occured an exception", e);
        } finally {
            lockFlag.remove();
        }
        return false;
    }
}
