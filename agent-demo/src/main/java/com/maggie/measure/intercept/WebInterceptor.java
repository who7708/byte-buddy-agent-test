package com.maggie.measure.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
public class WebInterceptor implements MethodInterceptor {

    public WebInterceptor() {

    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = methodInvocation.proceed();
        long end = System.currentTimeMillis();
        System.out.println("AOP 拦截 WEB 花费时间: " + (end - start) + " ms");
        return result;
    }
}
