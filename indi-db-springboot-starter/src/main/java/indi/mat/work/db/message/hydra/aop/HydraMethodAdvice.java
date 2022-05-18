package com.newegg.logistics.hydra.aop;

import com.newegg.logistics.hydra.core.HydraGatewayRequestHeaderContextHolder;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;



public class HydraMethodAdvice implements MethodBeforeAdvice{

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String className =o.getClass().getName() ;
        String methodName =method.getName() ;
        HydraGatewayRequestHeaderContextHolder.getMethodName().set(className+"#"+methodName);
    }

}
