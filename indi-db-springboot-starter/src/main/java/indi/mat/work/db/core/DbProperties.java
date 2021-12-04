package indi.mat.work.db.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "indi")
@Component
public class DbProperties {

    private boolean enable;
    private String version = "1.0";
    private String language = "Java";
    private String orm = "druid";
    private String serviceAop;
    private String serviceId;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOrm() {
        return orm;
    }

    public void setOrm(String orm) {
        this.orm = orm;
    }

    public String getServiceAop() {
        return serviceAop;
    }

    public void setServiceAop(String serviceAop) {
        this.serviceAop = serviceAop;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
