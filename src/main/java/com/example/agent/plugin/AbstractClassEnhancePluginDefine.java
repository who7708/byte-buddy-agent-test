package com.example.agent.plugin;

import com.example.agent.utils.LogUtils;
import com.example.agent.utils.StringUtil;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/23
 */
public abstract class AbstractClassEnhancePluginDefine {
    /**
     * Main entrance of enhancing the class.
     *
     * @param typeDescription target class description.
     * @param builder         byte-buddy's builder to manipulate target class's bytecode.
     * @param classLoader     load the given transformClass
     * @return the new builder, or <code>null</code> if not be enhanced.
     */
    public DynamicType.Builder<?> define(TypeDescription typeDescription,
                                         DynamicType.Builder<?> builder,
                                         ClassLoader classLoader,
                                         EnhanceContext context) {
        String interceptorDefineClassName = this.getClass().getName();
        String transformClassName = typeDescription.getTypeName();
        if (StringUtil.isEmpty(transformClassName)) {
            LogUtils.error("classname of being intercepted is not defined by  {}", interceptorDefineClassName);
            return null;
        }

        LogUtils.info("prepare to enhance class {} by  {}", transformClassName, interceptorDefineClassName);

        // /**
        //  * find witness classes for enhance class
        //  */
        // String[] witnessClasses = witnessClasses();
        // if (witnessClasses != null) {
        //     for (String witnessClass : witnessClasses) {
        //         if (!WitnessClassFinder.INSTANCE.exist(witnessClass, classLoader)) {
        //             LogUtils.warn("enhance class {} by plugin {} is not working. Because witness class {} is not existed.", transformClassName, interceptorDefineClassName, witnessClass);
        //             return null;
        //         }
        //     }
        // }

        /**
         * find origin class source code for interceptor
         */
        DynamicType.Builder<?> newClassBuilder = this.enhance(typeDescription, builder, classLoader, context);

        context.initializationStageCompleted();
        LogUtils.debug("enhance class {} by {} completely.", transformClassName, interceptorDefineClassName);

        return newClassBuilder;
    }

    protected abstract DynamicType.Builder<?> enhance(TypeDescription typeDescription,
                                                      DynamicType.Builder<?> newClassBuilder,
                                                      ClassLoader classLoader,
                                                      EnhanceContext context);
    //
    // /**
    //  * Define the {@link ClassMatch} for filtering class.
    //  *
    //  * @return {@link ClassMatch}
    //  */
    // protected abstract ClassMatch enhanceClass();
    //
    // /**
    //  * Witness classname list. Why need witness classname? Let's see like this: A library existed two released versions
    //  * (like 1.0, 2.0), which include the same target classes, but because of version iterator, they may have the same
    //  * name, but different methods, or different method arguments list. So, if I want to target the particular version
    //  * (let's say 1.0 for example), version number is obvious not an option, this is the moment you need "Witness
    //  * classes". You can add any classes only in this particular release version ( something like class
    //  * com.company.1.x.A, only in 1.0 ), and you can achieve the goal.
    //  */
    // protected String[] witnessClasses() {
    //     return new String[]{};
    // }
    //
    // public boolean isBootstrapInstrumentation() {
    //     return false;
    // }
    //
    // /**
    //  * Constructor methods intercept point. See {@link ConstructorInterceptPoint}
    //  *
    //  * @return collections of {@link ConstructorInterceptPoint}
    //  */
    // public abstract ConstructorInterceptPoint[] getConstructorsInterceptPoints();
    //
    // /**
    //  * Instance methods intercept point. See {@link InstanceMethodsInterceptPoint}
    //  *
    //  * @return collections of {@link InstanceMethodsInterceptPoint}
    //  */
    // public abstract InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints();
    //
    // /**
    //  * Static methods intercept point. See {@link StaticMethodsInterceptPoint}
    //  *
    //  * @return collections of {@link StaticMethodsInterceptPoint}
    //  */
    // public abstract StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints();
}
