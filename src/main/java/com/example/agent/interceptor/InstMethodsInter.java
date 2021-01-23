package com.example.agent.interceptor;

import com.example.agent.interceptor.enhance.EnhancedInstance;
import com.example.agent.interceptor.enhance.InstanceMethodsAroundInterceptor;
import com.example.agent.interceptor.enhance.MethodInterceptResult;
import com.example.agent.plugin.PluginException;
import com.example.agent.plugin.loader.InterceptorInstanceLoader;
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
     * An {@link InstanceMethodsAroundInterceptor} This name should only stay in {@link String}, the real {@link Class}
     * type will trigger classloader failure. If you want to know more, please check on books about Classloader or
     * Classloader appointment mechanism.
     */
    private InstanceMethodsAroundInterceptor interceptor;

    /**
     * @param instanceMethodsAroundInterceptorClassName class full name.
     */
    public InstMethodsInter(String instanceMethodsAroundInterceptorClassName, ClassLoader classLoader) {
        try {
            interceptor = InterceptorInstanceLoader.load(instanceMethodsAroundInterceptorClassName, classLoader);
        } catch (Throwable t) {
            throw new PluginException("Can't create InstanceMethodsAroundInterceptor.", t);
        }
    }

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

        MethodInterceptResult result = new MethodInterceptResult();
        try {
            interceptor.beforeMethod(targetObject, method, allArguments, method.getParameterTypes(), result);
        } catch (Throwable t) {
            LogUtils.error("class[{}] before method[{}] intercept failure {}", obj.getClass(), method.getName(), t);
        }
        long start = System.currentTimeMillis();

        Object ret = null;
        try {
            if (!result.isContinue()) {
                ret = result._ret();
            } else {
                ret = zuper.call();
            }
        } catch (Throwable t) {
            try {
                interceptor.handleMethodException(targetObject, method, allArguments, method.getParameterTypes(), t);
            } catch (Throwable t2) {
                LogUtils.error("class[{}] handle method[{}] exception failure {}", obj.getClass(), method.getName(), t2);
            }
            throw t;
        } finally {
            try {
                ret = interceptor.afterMethod(targetObject, method, allArguments, method.getParameterTypes(), ret);
            } catch (Throwable t) {
                LogUtils.error("class[{}] after method[{}] intercept failure", obj.getClass(), method.getName(), t);
            }

            LogUtils.info("SkyWalkingDynamicField {}", targetObject.getSkyWalkingDynamicField());
            LogUtils.info("方法名称: {}", method.getName());
            LogUtils.info("入参个数: {}", method.getParameterCount());
            LogUtils.info("入参类型: {}", Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).collect(Collectors.toList()));
            LogUtils.info("入参内容: {}", Arrays.asList(allArguments));
            LogUtils.info("出参类型: {}", method.getReturnType().getName());
            LogUtils.info("出参结果: {}", ret);
            LogUtils.info("方法耗时: {} ms", (System.currentTimeMillis() - start));
        }
        return ret;
    }
}
