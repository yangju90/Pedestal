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
public class HydraTransactionMessageStartBean extends HydraMessageBaseBean {
    @JsonProperty("ID")
    private String id;
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
}
