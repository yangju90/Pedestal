package indi.mat.work.project.config;

import indi.mat.work.project.vo.ext.RoleCacheInfo;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
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


    @Bean
    public JCacheCacheManager jCacheCacheManager(){
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        cacheManager.createCache("defaultCache",
                Eh107Configuration.fromEhcacheCacheConfiguration(defaultCache()));
        cacheManager.createCache("systemMenu", Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(String.class, List.class,2L, 60L)));
        cacheManager.createCache("role", Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(Long.class, RoleCacheInfo.class, 2L, 60L)));
        cacheManager.createCache("user", Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(Object.class, Object.class, 2L, 60L)));

        return new JCacheCacheManager(cacheManager);
    }



    private CacheConfiguration defaultCache() {
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10000L, EntryUnit.ENTRIES).build())
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ZERO))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(600L)))
                .build();
        return cacheConfiguration;
    }

    private CacheConfiguration cacheDefine(Class k, Class v, Long entriesSize, Long seconds) {
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(k, v, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(entriesSize, EntryUnit.ENTRIES)
                        .build())
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ZERO))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(seconds)))
                                .build();

        return cacheConfiguration;
    }


    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        return jCacheCacheManager();
    }
}
