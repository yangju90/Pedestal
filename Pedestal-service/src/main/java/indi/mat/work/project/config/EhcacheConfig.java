package indi.mat.work.project.config;

//import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableCaching
public class EhcacheConfig extends CachingConfigurerSupport {

    /**
     * 通过重写CacheManager ，覆盖AutoConfiguration 实现 EhCacheCacheManager
     * @return
     */

//    public net.sf.ehcache.CacheManager ehCacheManager() {
//        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
//        config.setName("EhCache");
//        config.setDynamicConfig(false);
//
//        config.defaultCache(defaultCache());
//        config.addCache(cacheDefine("systemMenu"));
//        config.addCache(cacheDefine("role"));
//        return net.sf.ehcache.CacheManager.newInstance(config);
//    }
//
//    private CacheConfiguration defaultCache() {
//        CacheConfiguration cacheConfiguration = new CacheConfiguration();
//        cacheConfiguration.eternal(false);
//        cacheConfiguration.setMaxEntriesLocalHeap(1000);
//        cacheConfiguration.overflowToOffHeap(false);
//        cacheConfiguration.setDiskPersistent(false);
//        cacheConfiguration.setTimeToIdleSeconds(600);
//        cacheConfiguration.timeToLiveSeconds(0);
//        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
//        return cacheConfiguration;
//    }
//
//    private CacheConfiguration cacheDefine(String cacheName) {
//        CacheConfiguration cacheConfiguration = new CacheConfiguration();
//        cacheConfiguration.setName(cacheName);
//        cacheConfiguration.eternal(false);
//        cacheConfiguration.setMaxEntriesLocalHeap(1000);
//        cacheConfiguration.setOverflowToDisk(false);
//        cacheConfiguration.setDiskSpoolBufferSizeMB(500);
//        cacheConfiguration.diskExpiryThreadIntervalSeconds(120);
//        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
//        cacheConfiguration.setTimeToIdleSeconds(0);
//        cacheConfiguration.timeToLiveSeconds(600);
//        return cacheConfiguration;
//    }


//    @Override
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheManager());
//    }
//
//    @Override
//    public CacheResolver cacheResolver() {
//        return null;
//    }
//
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new SimpleKeyGenerator();
//    }
//
//    @Override
//    public CacheErrorHandler errorHandler() {
//        return null;
//    }

    /**
     * 通过FactoryBean注册 Ehcache2.x
     *
     * 搭配配置文件使用
     */

//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/ehcache.xml"));
//        return ehCacheManagerFactoryBean;
//    }
//
//    @Bean
//    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
//        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
//    }
}
