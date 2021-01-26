package com.example.agent;

import com.example.agent.core.MyListener;
import com.example.agent.interceptor.ControllerAdvice;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.scaffold.TypeValidation;

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
public class HttpAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            // debug 需要暂停, 方便启动时 debug
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        LogUtils.info("基于javaagent链路追踪");
        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(Boolean.TRUE));
        new AgentBuilder.Default(byteBuddy)
                .with(new MyListener())
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(
                        nameStartsWith("com.example.demo")
                ).transform(
                (builder, typeDescription, classLoader, javaModule) -> builder.visit(
                        Advice.to(ControllerAdvice.class)
                                .on(isMethod()
                                        .and(not(isConstructor()))
                                        .and(not(isStatic()))
                                        .and(not(isSetter()))
                                        .and(not(isGetter()))
                                        .and(not(isToString()))
                                        .and(not(nameStartsWith("main"))))
                )).installOn(inst);
    }
}
