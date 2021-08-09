package indi.mat.work.project.config;

import indi.mat.work.project.cache.EhCacheManagerFactoryBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@EnableCaching
public class EhcacheConfig extends CachingConfigurerSupport {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        String s = "<ehcache>\n" +
                "\n" +
                "    <defaultCache\n" +
                "            maxElementsInMemory=\"10000\"\n" +
                "            eternal=\"false\"\n" +
                "            timeToIdleSeconds=\"3600\"\n" +
                "            timeToLiveSeconds=\"0\"\n" +
                "            overflowToDisk=\"false\"\n" +
                "            diskPersistent=\"false\"\n" +
                "            diskExpiryThreadIntervalSeconds=\"120\"/>\n" +
                "\n" +
                "    <cache\n" +
                "            name=\"user\"\n" +
                "            maxEntriesLocalHeap=\"2000\"\n" +
                "            eternal=\"false\"\n" +
                "            timeToIdleSeconds=\"3600\"\n" +
                "            timeToLiveSeconds=\"0\"\n" +
                "            overflowToDisk=\"false\"\n" +
                "            statistics=\"true\">\n" +
                "    </cache>\n" +
                "\n" +
                "</ehcache>";
        ehCacheManagerFactoryBean.setConfigLocation(s);
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }
}
