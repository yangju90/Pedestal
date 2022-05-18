package indi.mat.work.mq.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 事务数据
 *
 */
public class MqMessageTransactionBean {
    // 事务的全局唯一标识
    // [必选]
    @JsonProperty("Identifier")
    private String identifier;
    // 事务的隔离级别
    // [可选]
    @JsonProperty("isolationLevel")
    private String isolationLevel;
    // 事务创建的UTC时间，时区UTC，类型 Unix Epoch, 单位毫秒
    // [必选]
    @JsonProperty("Created")
    private long created;
    // [可选]
    @JsonProperty("Type")
    private String type;

    public MqMessageTransactionBean(String identifier, long created) {
        this.identifier = identifier;
        this.created = created;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIsolationLevel() {
        return isolationLevel;
    }

    public void setIsolationLevel(String isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
