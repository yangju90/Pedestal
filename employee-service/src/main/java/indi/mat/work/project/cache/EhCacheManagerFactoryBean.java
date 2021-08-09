package indi.mat.work.project.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class EhCacheManagerFactoryBean implements FactoryBean<CacheManager>, InitializingBean, DisposableBean {

    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private String configLocation;

    @Nullable
    private String cacheManagerName;

    private boolean acceptExisting = false;

    private boolean shared = false;

    @Nullable
    private CacheManager cacheManager;

    private boolean locallyManaged = true;

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public void setCacheManagerName(String cacheManagerName) {
        this.cacheManagerName = cacheManagerName;
    }

    public void setAcceptExisting(boolean acceptExisting) {
        this.acceptExisting = acceptExisting;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }


    @Override
    public void afterPropertiesSet() throws CacheException {
        if (logger.isInfoEnabled()) {
            logger.info("Initializing EhCache CacheManager" +
                    (this.cacheManagerName != null ? " '" + this.cacheManagerName + "'" : ""));
        }

        Configuration configuration = (this.configLocation != null ?
                parseConfiguration(this.configLocation) : ConfigurationFactory.parseConfiguration());
        if (this.cacheManagerName != null) {
            configuration.setName(this.cacheManagerName);
        }

        if (this.shared) {
            this.cacheManager = CacheManager.create(configuration);
        } else if (this.acceptExisting) {
            synchronized (CacheManager.class) {
                this.cacheManager = CacheManager.getCacheManager(this.cacheManagerName);
                if (this.cacheManager == null) {
                    this.cacheManager = new CacheManager(configuration);
                } else {
                    this.locallyManaged = false;
                }
            }
        } else {
            this.cacheManager = new CacheManager(configuration);
        }
    }


    @Override
    @Nullable
    public CacheManager getObject() {
        return this.cacheManager;
    }

    @Override
    public Class<? extends CacheManager> getObjectType() {
        return (this.cacheManager != null ? this.cacheManager.getClass() : CacheManager.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void destroy() {
        if (this.cacheManager != null && this.locallyManaged) {
            if (logger.isInfoEnabled()) {
                logger.info("Shutting down EhCache CacheManager" +
                        (this.cacheManagerName != null ? " '" + this.cacheManagerName + "'" : ""));
            }
            this.cacheManager.shutdown();
        }
    }


    public Configuration parseConfiguration(String configLocation) throws CacheException {
        try {
            try(InputStream is = new ByteArrayInputStream(configLocation.getBytes())){
                return ConfigurationFactory.parseConfiguration(is);
            }
        } catch (IOException ex) {
            throw new CacheException("Failed to parse EhCache configuration resource", ex);
        }
    }

}
