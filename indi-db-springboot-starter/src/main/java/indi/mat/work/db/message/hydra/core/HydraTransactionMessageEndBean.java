package com.newegg.logistics.hydra.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * sql执行结束数据
 *
 * @author nl67
 * @since 1.1.0 2019-07-18
 */
@JsonInclude(Include.NON_NULL)
public class HydraTransactionMessageEndBean extends HydraMessageBaseBean {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("EndTimeUtc")
    private long endTimeUtc;
    @JsonProperty("Status")
    private String status;
    @JsonIgnore
    private String method;
    @JsonIgnore
    private String dbTraceID;
    @JsonIgnore
    private String scriptName;
    @JsonIgnore
    private long commandTimeout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEndTimeUtc() {
        return endTimeUtc;
    }

    public void setEndTimeUtc(long endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
