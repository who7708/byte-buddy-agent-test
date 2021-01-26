package com.example.agent.core;

import com.example.agent.plugin.AbstractClassEnhancePluginDefine;
import com.example.agent.plugin.EnhanceContext;
import com.example.agent.utils.LogUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/25
 */
public class MyTransformer implements AgentBuilder.Transformer {
    private AbstractClassEnhancePluginDefine define;

    public MyTransformer(AbstractClassEnhancePluginDefine define) {
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
