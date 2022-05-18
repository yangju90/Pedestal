package indi.mat.work.mq.core;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * Request Parameter 上下文保持器。
 */
public class MqGatewayRequestHeaderContextHolder {

    private static final ThreadLocal<Map<String, String>> REQUEST_LOCAL = new TransmittableThreadLocal<>();
    private static final ThreadLocal<MqMessageBaseBean> MSG_LOCAL = new TransmittableThreadLocal<>();
    private static final ThreadLocal<String> methodName = new TransmittableThreadLocal<>();

    public static void removeTransactionMessage() {
        MSG_LOCAL.remove();
    }

    public static Map<String, String> getRequestHeaders() {
        return REQUEST_LOCAL.get();
    }

    public static void addRequestHeaders(Map<String, String> headers) {
        REQUEST_LOCAL.remove();
        REQUEST_LOCAL.set(headers);
    }

    public static void removeRequestHeaders() {
        MSG_LOCAL.remove();
        REQUEST_LOCAL.remove();
        methodName.remove();
    }

    public static ThreadLocal<MqMessageBaseBean> getMsgLocal() {
        return MSG_LOCAL;
    }

    public static ThreadLocal<String> getMethodName() {
        return methodName;
    }
}
