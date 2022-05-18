package com.newegg.logistics.hydra.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * SQL执行开始数据
 *
 * @author nl67
 * @since 1.1.0 2019-07-18
 */
@JsonInclude(Include.NON_NULL)
public class HydraOperationMessageStartBean extends HydraMessageBaseBean {
    // 数据库操作执行的 SQL 脚本
    // [StartEvent:必选, EndEvent: 不用提供，节省流量]
    @JsonProperty("SqlContent")
    private String sqlContent;
    // 数据库操作所用到的参数以及其参数值等信息，该属性为集合类型，其中可能包含0个或者多个参数对象
    // [StartEvent:必选, EndEvent: 不用提供，节省流量]
    @JsonProperty("Parameters")
    private List<HydraSqlParameterBean> parameters;
    // 事务对象，用于标识该数据库操作包含在一个事务中，如果没有开启事务，该属性不提供
    // [可选]
    @JsonProperty("Transaction")
    private HydraMessageTransactionBean transaction;

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public List<HydraSqlParameterBean> getParameters() {
        return parameters;
    }

    public void setParameters(List<HydraSqlParameterBean> parameters) {
        this.parameters = parameters;
    }

    public HydraMessageTransactionBean getTransaction() {
        return transaction;
    }

    public void setTransaction(HydraMessageTransactionBean transaction) {
        this.transaction = transaction;
    }
}
