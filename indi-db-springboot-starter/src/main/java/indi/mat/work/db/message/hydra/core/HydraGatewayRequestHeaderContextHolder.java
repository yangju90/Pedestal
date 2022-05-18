package com.newegg.logistics.hydra.core;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * Hydra Geteway Request Parameter 上下文保持器。
 * <p>适用于Newegg Hydra。
 *
 * @author vc80
 * @since 1.2.9 2019-10-22
 */
public class HydraGatewayRequestHeaderContextHolder {

    private static final ThreadLocal<Map<String, String>> REQUEST_LOCAL = new TransmittableThreadLocal<>();
    private static final ThreadLocal<HydraMessageBaseBean> MSG_LOCAL = new TransmittableThreadLocal<>();
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

    public static ThreadLocal<HydraMessageBaseBean> getMsgLocal() {
        return MSG_LOCAL;
    }

    public static ThreadLocal<String> getMethodName() {
        return methodName;
    }
}
