package indi.mat.work.db.aop;

import indi.mat.work.db.util.ThreadLocalUtils;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MethodNameAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String className = o.getClass().getName();
        String methodName = method.getName();
        ThreadLocalUtils.setMethodName(className + "#" + methodName);
    }
}
