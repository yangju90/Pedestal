@Configuration
@EnableCaching
public class EhcacheConfig extends CachingConfigurerSupport {

    @Bean
    public JCacheCacheManager jCacheCacheManager(){
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        cacheManager.createCache("defaultCache",
                Eh107Configuration.fromEhcacheCacheConfiguration(defaultCache()));
        cacheManager.createCache(CacheManagerNameConstant.SYSTEM_MENU_CACHE, Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(String.class, List.class,2L, 60L)));
        cacheManager.createCache("role", Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(Long.class, RoleCacheInfo.class, 2L, 60L)));
        cacheManager.createCache("system", Eh107Configuration.fromEhcacheCacheConfiguration(cacheDefine(String.class, SystemMenu.class, 2L, 2L)));

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
