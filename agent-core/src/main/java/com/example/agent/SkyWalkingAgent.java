package com.example.agent;

import com.example.agent.core.MyListener;
import com.example.agent.core.MyTransformer;
import com.example.agent.interceptor.enhance.ClassEnhancePluginDefine;
import com.example.agent.plugin.AbstractClassEnhancePluginDefine;
import com.example.agent.plugin.loader.AgentClassLoader;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/23
 */
public class SkyWalkingAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        try {
            // debug 需要暂停, 方便启动时 debug
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        LogUtils.info("开始启动 SkyWalking Agent...");
        AgentClassLoader.initDefaultLoader();
        final AbstractClassEnhancePluginDefine define = new ClassEnhancePluginDefine();
        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(Boolean.TRUE));
        AgentBuilder agentBuilder = new AgentBuilder.Default(byteBuddy)
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic()));

        agentBuilder
                // .type(nameStartsWith())
                // 要拦截的类
                .type(nameStartsWith("com.example.demo")
                        .and(not(isInterface()))
                        .and(not(isStatic()))
                )
                // .type(any()
                //         .and(not(isInterface()))
                //         .and(not(isStatic()))
                // )
                .transform(new AgentBuilder.Transformer.ForAdvice()
                        .include(ClassLoader.getSystemClassLoader())
                        // .advice()
                )
                .transform(new MyTransformer(define))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(new MyListener())
                .installOn(instrumentation);

        // Runtime.getRuntime()
        //         .addShutdownHook(new Thread(ServiceManager.INSTANCE::shutdown, "skywalking service shutdown thread"));
    }

}
