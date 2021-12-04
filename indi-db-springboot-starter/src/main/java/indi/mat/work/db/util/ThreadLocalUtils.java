package indi.mat.work.db.util;

public class ThreadLocalUtils {
    private static final ThreadLocal<String> methodName = new InheritableThreadLocal<>();

    private static final ThreadLocal<String> traceId = new InheritableThreadLocal<>();



    public static void setMethodName(String name) {
        methodName.set(name);
    }

    public static String getMethodName() {
        return methodName.get();
    }


    public static String getTraceId() {
        return traceId.get();
    }

    public static void setTraceId(String value) {
        traceId.set(value);
    }

    public static void clear(){
        methodName.remove();
        traceId.remove();
    }
}
