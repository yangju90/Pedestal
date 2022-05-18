package indi.mat.work.mq.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * kafka key 信息
 */
public class MqKafkaMessgeKeyBean {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Key")
    private String key;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Source")
    private String source;

    public MqKafkaMessgeKeyBean(String type, String key, String version) {
        this.type = type;
        this.key = key;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
