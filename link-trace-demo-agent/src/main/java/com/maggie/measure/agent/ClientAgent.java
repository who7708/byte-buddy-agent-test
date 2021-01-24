package com.maggie.measure.agent;

import com.maggie.measure.intercept.MyControllerAdvice;
import com.maggie.measure.intercept.MyHttpAdvice;
import com.maggie.measure.intercept.MyServiceAdvice;
import com.maggie.measure.loader.AgentClassLoader;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class ClientAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("==============Client=============== premain =============start============");
        AgentClassLoader.initDefaultLoader();
        AgentBuilder.Transformer transformerWeb = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                return builder
                        .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(MyControllerAdvice.class)); // 委托
            }
        };
        AgentBuilder.Transformer transformerService = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                return builder
                        .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(MyServiceAdvice.class)); // 委托
            }
        };
        AgentBuilder.Transformer transformerHttp = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                return builder
                        .method(ElementMatchers.isMethod().and(ElementMatchers.<MethodDescription>named("doExecute")
                                .or(ElementMatchers.named("execute")))
                                .and(ElementMatchers.takesArgument(0, ElementMatchers.named("org.apache.http.client.methods.HttpUriRequest")))
                                .and(ElementMatchers.takesArguments(1))) // 拦截任意方法
                        .intercept(MethodDelegation.to(MyHttpAdvice.class)); // 委托
            }
        };
        // 拦截 Web
        AgentBuilder agentBuilder = new AgentBuilder
                .Default()
                .type(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.RestController"))) // 指定需要拦截的类
                .transform(transformerWeb);
        // 拦截 Service
        agentBuilder = agentBuilder.type(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.stereotype.Service"))) // 指定需要拦截的类
                .transform(transformerService);
        // 拦截 Http
        agentBuilder = agentBuilder.type(ElementMatchers.hasSuperType(ElementMatchers.named("org.apache.http.impl.client.AbstractHttpClient"))
                .or(ElementMatchers.named("org.apache.http.impl.client.CloseableHttpClient"))) // 指定需要拦截的类
                .transform(transformerHttp);
        // 注入 inst
        agentBuilder.installOn(inst);
        System.out.println("================Client============ premain ================finish===========");
    }
}