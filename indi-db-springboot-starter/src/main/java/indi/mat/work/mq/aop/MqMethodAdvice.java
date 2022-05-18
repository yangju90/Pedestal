package indi.mat.work.mq.aop;

import indi.mat.work.mq.core.MqGatewayRequestHeaderContextHolder;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;



public class MqMethodAdvice implements MethodBeforeAdvice{

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String className =o.getClass().getName() ;
        String methodName =method.getName() ;
        MqGatewayRequestHeaderContextHolder.getMethodName().set(className+"#"+methodName);
    }

}
