package com.example.agent.interceptor;

import com.example.agent.interceptor.enhance.EnhancedInstance;
import com.example.agent.utils.GsonUtils;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/23
 */
public class InstMethodsInter {

    /**
     * Intercept the target instance method.
     *
     * @param obj          target class instance.
     * @param allArguments all method arguments
     * @param method       method description.
     * @param zuper        the origin call ref.
     * @return the return value of target instance method.
     * @throws Exception only throw exception because of zuper.call() or unexpected exception in sky-walking ( This is a
     *                   bug, if anything triggers this condition ).
     */
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        EnhancedInstance targetObject = (EnhancedInstance) obj;
        long start = System.currentTimeMillis();
        Object ret = null;
        try {
            ret = zuper.call();
        } catch (Throwable t) {
            throw t;
        } finally {
            LogUtils.info("SkyWalkingDynamicField {}", targetObject.getSkyWalkingDynamicField());
            LogUtils.info("方法名称: {}", method.getName());
            LogUtils.info("入参个数: {}", method.getParameterCount());
            LogUtils.info("入参类型: {}", Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).collect(Collectors.toList()));
            // LogUtils.info("入参内容: {}", GsonUtils.toJson(allArguments));
            LogUtils.info("入参内容: {}", Arrays.asList(allArguments));
            LogUtils.info("出参类型: {}", method.getReturnType().getName());
            LogUtils.info("出参结果: {}", GsonUtils.toJson(ret));
            LogUtils.info("方法耗时: {} ms\n\n", (System.currentTimeMillis() - start));
        }
        return ret;
    }
}
