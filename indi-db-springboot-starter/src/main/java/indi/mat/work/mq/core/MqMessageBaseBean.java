package indi.mat.work.mq.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * kafka上传数据的base公共数据
 *
 */
public class MqMessageBaseBean {
    // Trace ID 用于标识一个业务处理流程，其中会关联一个或者多个API调用，每一个API调用用 SpanID 来标识
    // [可选]
    @JsonProperty("TraceID")
    private String traceID;
    // 如上面 Trace ID的说明所述，SpanID 标识一次 API 调用
    // [可选]
    @JsonProperty("SpanID")
    private String spanID = "";
    // Service ID 用于标识一个业务服务，由 Genesis 创建项目的过程中生成，是用户指定的，也对应到源码管理库中的项目名称。
    // 走Genesis build的服务可以来自环境变量: $SERVICE_IDENTITY，也可以服务自行确定。
    // [必选]
    @JsonProperty("ServiceID")
    private String serviceID;
    // Git 的提交版本，标识当前业务服务的代码版本标识。
    // 来自环境变量: $COMMIT_ID
    // [可选]
    @JsonProperty("GitCommitID")
    private String gitCommitID = "";
    // 一个 API 调用 可能会包含一个或者多个数据库操作，每个数据库操作用 DBTraceID标识。
    // 一般情况下一个数据库操作会产生两个这样的 Trace 消息，一个是操作开始时发出，一个是操作结束时发出。其 DBTraceID 是相同的。
    // [必选]
    @JsonProperty("DBTraceID")
    private String dbTraceID;
    // ScriptName 用于在一个 Service 内标识一类数据库操作。
    // [必选]
    @JsonProperty("ScriptName")
    private String scriptName;
    // 数据库实例别名，由开发团队在项目中指定，不一定对应一台DB Server，如果对应语言平台没有这个信息，可以不提供
    // [可选]
    @JsonProperty("DBInstance")
    private String dbInstance = "";
    // 数据库操作方法名称，目前不用于分析和统计
    // [可选]
    @JsonProperty("Method")
    private String method = "";
    // 业务服务实例所运行的宿主机名称，如果在Docker中运行，该名称可能是 docker container ID, 否则可能是机器名称
    // [可选]
    @JsonProperty("HostName")
    private String hostName = "";
    // 本数据库操作的执行开始时间，时区UTC，类型 Unix Epoch, 单位毫秒
    // [必选]
    @JsonProperty("StartTimeUtc")
    long startTimeUtc;
    // 服务中代码或者配置指定的数据库操作超时时间，暂不用于分析和统计
    // [可选]
    @JsonProperty("CommandTimeout")
    private long commandTimeout;
    @JsonProperty("Server")
    private String server = "";
    @JsonProperty("OriginalDBServer")
    private String originalDBServer = "";
    @JsonProperty("UserID")
    private String userID = "";

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getSpanID() {
        return spanID;
    }

    public void setSpanID(String spanID) {
        this.spanID = spanID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getGitCommitID() {
        return gitCommitID;
    }

    public void setGitCommitID(String gitCommitID) {
        this.gitCommitID = gitCommitID;
    }

    public String getDbTraceID() {
        return dbTraceID;
    }

    public void setDbTraceID(String dbTraceID) {
        this.dbTraceID = dbTraceID;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getDbInstance() {
        return dbInstance;
    }

    public void setDbInstance(String dbInstance) {
        this.dbInstance = dbInstance;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public long getStartTimeUtc() {
        return startTimeUtc;
    }

    public void setStartTimeUtc(long startTimeUtc) {
        this.startTimeUtc = startTimeUtc;
    }

    public long getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(long commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getOriginalDBServer() {
        return originalDBServer;
    }

    public void setOriginalDBServer(String originalDBServer) {
        this.originalDBServer = originalDBServer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
