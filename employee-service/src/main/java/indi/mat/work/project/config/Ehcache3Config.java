package indi.mat.work.project.config;

import indi.mat.work.project.cache.CacheAttribute;
import indi.mat.work.project.cache.CacheManagerNameConstant;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class Ehcache3Config extends CachingConfigurerSupport {

    /**
     * Ehcache 3.x
     * 参考的资料链接，核心考虑JSR107
     * 自动注入实现了JCacheCacheManager，JCacheCacheManager提供了javax.cache.CacheManager的实现
     *
     * https://www.ehcache.org/documentation/3.0/107.html
     * https://blog.csdn.net/Damien_J_Scott/article/details/117422287?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-13.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-13.control
     * 
     * 配置参数参考
     * https://blog.csdn.net/qq_28988969/article/details/78210873  
     * @return
     */


    private static final Logger logger = LoggerFactory.getLogger(EhcacheConfig.class);

    @Autowired
    private CacheAttribute cacheAttribute;


    @Override
    @Bean
    public CacheManager cacheManager() {
        return new JCacheCacheManager(jCacheCacheManager());
    }

    public javax.cache.CacheManager jCacheCacheManager(){
        CachingProvider provider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = provider.getCacheManager();
        createCache(cacheManager, "defaultCache", defaultCache());
        createCache(cacheManager, CacheManagerNameConstant.SYSTEM_MENU_CACHE, cacheDefine(200L, 60L));
        List<String> names = cacheAttribute.getNameList();
        for(String name : names) {
            createCache(cacheManager, name, cacheDefine(cacheAttribute.getMaxHeap(), cacheAttribute.getTti()));
        }
        return cacheManager;
    }



    private CacheConfiguration defaultCache() {
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10000L, EntryUnit.ENTRIES).build())
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ZERO))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(3600L)))
                .build();
        return cacheConfiguration;
    }

    private CacheConfiguration cacheDefine(Long entriesSize, Long seconds) {
        CacheConfiguration<Object, Object> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(entriesSize, EntryUnit.ENTRIES)
                        .build())
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ZERO))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(seconds)))
                                .build();

        return cacheConfiguration;
    }

    private void createCache(javax.cache.CacheManager cacheManager, String name, CacheConfiguration configuration){
        // 这里的判断主要为了 Spring boot test 测试时，会多次Create同一个name的CacheManager，判定
        if(cacheManager.getCache(name) == null) {
            cacheManager.createCache(name,
                    Eh107Configuration.fromEhcacheCacheConfiguration(configuration));
        }else{
            logger.info("A Cache named [" +name+ "] already exists");
        }
    }
}
