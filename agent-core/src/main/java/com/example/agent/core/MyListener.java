package com.example.agent.core;

import com.example.agent.utils.LogUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.io.IOException;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/25
 */
public class MyListener implements AgentBuilder.Listener {
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
            dynamicType.saveIn(new File(home + "/IdeaProjects/byte-buddy-agent-test/dist/agent-core/debugging"));
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
