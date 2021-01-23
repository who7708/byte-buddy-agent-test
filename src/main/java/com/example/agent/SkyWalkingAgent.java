package com.example.agent;

import com.example.agent.interceptor.enhance.ClassEnhancePluginDefine;
import com.example.agent.plugin.AbstractClassEnhancePluginDefine;
import com.example.agent.plugin.EnhanceContext;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.io.IOException;
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
        final AbstractClassEnhancePluginDefine define = new ClassEnhancePluginDefine();
        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(Boolean.TRUE));
        AgentBuilder agentBuilder = new AgentBuilder.Default(byteBuddy).ignore(
                nameStartsWith("net.bytebuddy.")
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
                .transform(new Transformer(define))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(new Listener())
                .installOn(instrumentation);

        // Runtime.getRuntime()
        //         .addShutdownHook(new Thread(ServiceManager.INSTANCE::shutdown, "skywalking service shutdown thread"));
    }

    private static class Transformer implements AgentBuilder.Transformer {
        private AbstractClassEnhancePluginDefine define;

        Transformer(AbstractClassEnhancePluginDefine define) {
            this.define = define;
        }

        @Override
        public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                final TypeDescription typeDescription,
                                                final ClassLoader classLoader,
                                                final JavaModule module) {
            DynamicType.Builder<?> newBuilder = builder;
            EnhanceContext context = new EnhanceContext();
            // for (AbstractClassEnhancePluginDefine define : pluginDefines) {
            DynamicType.Builder<?> possibleNewBuilder = define.define(typeDescription, newBuilder, classLoader, context);
            if (possibleNewBuilder != null) {
                newBuilder = possibleNewBuilder;
            }
            // }
            if (context.isEnhanced()) {
                LogUtils.debug("Finish the prepare stage for {}.", typeDescription.getName());
            }

            return newBuilder;
        }
    }

    private static class Listener implements AgentBuilder.Listener {
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            // LogUtils.info("On Discovery  {}", typeName);
        }

        @Override
        public void onTransformation(final TypeDescription typeDescription,
                                     final ClassLoader classLoader,
                                     final JavaModule module,
                                     final boolean loaded,
                                     final DynamicType dynamicType) {
            LogUtils.info("On Transformation class  {}\n", typeDescription.getName());
            String home = System.getProperty("user.home");
            try {
                dynamicType.saveIn(new File(home + "/IdeaProjects/byte-buddy-agent-test/byte-buddy-agent-test/debugging"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onIgnored(final TypeDescription typeDescription,
                              final ClassLoader classLoader,
                              final JavaModule module,
                              final boolean loaded) {
            // LogUtils.info("On Ignore  {}", typeDescription.getName());
        }

        @Override
        public void onError(final String typeName,
                            final ClassLoader classLoader,
                            final JavaModule module,
                            final boolean loaded,
                            final Throwable throwable) {
            LogUtils.info("On Error {} error {}", typeName, throwable);
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            // LogUtils.info("On Complete  {}", typeName);
        }
    }
}
