package indi.mat.work.mq.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * mq配置项。
 * <p>
 *
 */
@ConfigurationProperties(prefix = "hydra")
@Component
public class MqProperties {
    private boolean enable;
    private String version = "1.0";
    private String language = "Java";
    private String orm = "druid";
    private String serviceAop;
    private String serviceId;
    private Map<String, Object> kafkaProducer; // 支持设定各种kafka参数

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

    public Map<String, Object> getKafkaProducer() {
        return kafkaProducer;
    }

    public void setKafkaProducer(Map<String, Object> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        if(!kafkaProducer.containsKey("acks")){
            kafkaProducer.put("acks","0");
        }
        if(!kafkaProducer.containsKey("retries")){
            kafkaProducer.put("retries","0");
        }
        if(!kafkaProducer.containsKey("max.block.ms")){
            kafkaProducer.put("max.block.ms","500");
        }
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getServiceAop() {
        return serviceAop;
    }

    public void setServiceAop(String serviceAop) {
        this.serviceAop = serviceAop;
    }
}
