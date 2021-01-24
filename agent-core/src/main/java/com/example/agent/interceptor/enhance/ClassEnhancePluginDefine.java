package com.example.agent.interceptor.enhance;

import com.example.agent.constants.SwConstants;
import com.example.agent.interceptor.InstMethodsInter;
import com.example.agent.interceptor.enhance.impl.InstanceMethodsAroundInterceptorImpl;
import com.example.agent.plugin.AbstractClassEnhancePluginDefine;
import com.example.agent.plugin.EnhanceContext;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.jar.asm.Opcodes.ACC_PRIVATE;
import static net.bytebuddy.jar.asm.Opcodes.ACC_VOLATILE;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/23
 */
public class ClassEnhancePluginDefine extends AbstractClassEnhancePluginDefine {
    @Override
    protected DynamicType.Builder<?> enhance(TypeDescription typeDescription,
                                             DynamicType.Builder<?> newClassBuilder,
                                             ClassLoader classLoader,
                                             EnhanceContext context) {
        if (!context.isObjectExtended()) {
            newClassBuilder = newClassBuilder
                    .defineField(SwConstants.CONTEXT_ATTR_NAME, Object.class, ACC_PRIVATE | ACC_VOLATILE)
                    .implement(EnhancedInstance.class)
                    .intercept(FieldAccessor.ofField(SwConstants.CONTEXT_ATTR_NAME));
            context.extendObjectCompleted();
        }

        ElementMatcher.Junction<MethodDescription> junction = not(isStatic()).and(isDeclaredBy(typeDescription));
        // if (instanceMethodsInterceptPoint instanceof DeclaredInstanceMethodsInterceptPoint) {
        //     junction = junction.and(ElementMatchers.<MethodDescription>isDeclaredBy(typeDescription));
        // }
        // if (instanceMethodsInterceptPoint.isOverrideArgs()) {
        //     if (isBootstrapInstrumentation()) {
        //         newClassBuilder = newClassBuilder.method(junction)
        //                 .intercept(MethodDelegation.withDefaultConfiguration()
        //                         .withBinders(Morph.Binder.install(OverrideCallable.class))
        //                         .to(BootstrapInstrumentBoost.forInternalDelegateClass(interceptor)));
        //     } else {
        //         newClassBuilder = newClassBuilder.method(junction)
        //                 .intercept(MethodDelegation.withDefaultConfiguration()
        //                         .withBinders(Morph.Binder.install(OverrideCallable.class))
        //                         .to(new InstMethodsInterWithOverrideArgs(interceptor, classLoader)));
        //     }
        // } else {
        //     if (isBootstrapInstrumentation()) {
        //         newClassBuilder = newClassBuilder.method(junction)
        //                 .intercept(MethodDelegation.withDefaultConfiguration()
        //                         .to(BootstrapInstrumentBoost.forInternalDelegateClass(interceptor)));
        //     } else {
        //         newClassBuilder = newClassBuilder.method(junction)
        //                 .intercept(MethodDelegation.withDefaultConfiguration()
        //                         .to(new InstMethodsInter(interceptor, classLoader)));
        // }
        // }

        return newClassBuilder
                .method(junction)
                .intercept(MethodDelegation.withDefaultConfiguration()
                        .to(new InstMethodsInter(InstanceMethodsAroundInterceptorImpl.class.getName(), classLoader)));
                        // .to(new InstMethodsInter()));
    }
}