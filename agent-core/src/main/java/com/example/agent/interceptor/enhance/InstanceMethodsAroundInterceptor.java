package com.example.agent.interceptor.enhance;

import java.lang.reflect.Method;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/23
 */
public interface InstanceMethodsAroundInterceptor {
    /**
     * called before target method invocation.
     *
     * @param result change this result, if you want to truncate the method.
     */
    void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                      MethodInterceptResult result) throws Throwable;

    /**
     * called after target method invocation. Even method's invocation triggers an exception.
     *
     * @param ret the method's original return value. May be null if the method triggers an exception.
     * @return the method's actual return value.
     */
    Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                       Object ret) throws Throwable;

    /**
     * called when occur exception.
     *
     * @param t the exception occur.
     */
    void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
                               Class<?>[] argumentsTypes, Throwable t);
}
