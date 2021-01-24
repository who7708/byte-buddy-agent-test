package com.example.agent.interceptor.enhance.impl;

import com.example.agent.interceptor.enhance.EnhancedInstance;
import com.example.agent.interceptor.enhance.InstanceMethodsAroundInterceptor;
import com.example.agent.interceptor.enhance.MethodInterceptResult;
import com.example.agent.utils.LogUtils;

import java.lang.reflect.Method;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
public class InstanceMethodsAroundInterceptorImpl implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        LogUtils.info("InstanceMethodsAroundInterceptor.beforeMethod");
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        LogUtils.info("InstanceMethodsAroundInterceptor.afterMethod");
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {
        LogUtils.info("InstanceMethodsAroundInterceptor.handleMethodException");
    }
}
