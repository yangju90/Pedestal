package indi.mat.work.mq.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * sql执行结束数据
 *
 */
@JsonInclude(Include.NON_NULL)
public class MqOperationMessageEndBean extends MqMessageBaseBean {
    // 数据库操作的失败信息，如果操作失败，该字段会包含较详细的失败原因。如果数据库操作成功，该属性不提供
    // [可选]
    @JsonProperty("Message")
    private String message;
    // 数据库操作失败时的堆栈信息，如果操作失败，该字段会包含用于定位代码位置的堆栈信息。 如果数据库操作成功，该属性不提供
    // [可选]
    @JsonProperty("StackTrace")
    private String stackTrace;
    // 标识数据库操作的执行结果，取值只会有 Succeed和 Failed 两种, 如果 Trace 消息是操作开始时发出的，该属性不会提供，只有操作结束时的
    // Trace 消息才会提供该属性
    // 有效值: Succeed, Failed
    // [必选]
    @JsonProperty("Result")
    private String result;
    // 数据库操作的执行结束时间，时区UTC，类型 Unix Epoch, 单位毫秒
    // 如果 Trace 消息是操作开始时发出的，该属性不会提供，只有操作结束时的 Trace 消息才会提供该属性
    // [StartEvent:不用提供, EndEvent: 必选]
    @JsonProperty("EndTimeUtc")
    private long endTimeUtc;

    // 事务对象，用于标识该数据库操作包含在一个事务中，如果没有开启事务，该属性不提供
    // [可选]
    @JsonProperty("Transaction")
    private MqMessageTransactionBean transaction;
    // 扩展属性，只在 End 事件消息中提供。
    @JsonProperty("ExtendProperties")
    private List<Map<String, String>> extendProperties;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getEndTimeUtc() {
        return endTimeUtc;
    }

    public void setEndTimeUtc(long endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }

    public MqMessageTransactionBean getTransaction() {
        return transaction;
    }

    public void setTransaction(MqMessageTransactionBean transaction) {
        this.transaction = transaction;
    }

    public List<Map<String, String>> getExtendProperties() {
        return extendProperties;
    }

    public void setExtendProperties(List<Map<String, String>> extendProperties) {
        this.extendProperties = extendProperties;
    }
}
