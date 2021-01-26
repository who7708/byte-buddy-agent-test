package com.example.agent;

import com.example.agent.core.MyListener;
import com.example.agent.interceptor.MyAdvice;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isConstructor;
import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.isToString;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/25
 */
public class MyAgent {

    // JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            // debug 需要暂停, 方便启动时 debug
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        LogUtils.info("基于javaagent链路追踪");

        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(Boolean.TRUE));
        AgentBuilder agentBuilder = new AgentBuilder.Default(byteBuddy);
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                builder = builder.visit(
                        Advice.to(MyAdvice.class)
                                .on(isMethod()
                                        .and(not(isConstructor()))
                                        .and(not(isStatic()))
                                        .and(not(isSetter()))
                                        .and(not(isGetter()))
                                        .and(not(isToString()))
                                        .and(not(nameStartsWith("main")))));
                return builder;
            }
        };

        agentBuilder.type(
                nameStartsWith("com.example.demo"))
                .transform(transformer)
                .with(new MyListener())
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .installOn(inst);

    }
}
