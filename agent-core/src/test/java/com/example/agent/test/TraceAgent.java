// package com.example.agent.test;
//
// import com.javashizhan.trace.interceptor.AbstractJunction;
// import com.javashizhan.trace.interceptor.ProtectiveShieldMatcher;
// import com.javashizhan.trace.interceptor.TraceInterceptor;
// import net.bytebuddy.agent.builder.AgentBuilder;
// import net.bytebuddy.description.NamedElement;
// import net.bytebuddy.description.type.TypeDescription;
// import net.bytebuddy.implementation.MethodDelegation;
// import net.bytebuddy.matcher.ElementMatcher;
// import net.bytebuddy.matcher.ElementMatchers;
//
// import java.lang.instrument.Instrumentation;
//
// import static net.bytebuddy.matcher.ElementMatchers.isInterface;
// import static net.bytebuddy.matcher.ElementMatchers.isSetter;
// import static net.bytebuddy.matcher.ElementMatchers.nameContainsIgnoreCase;
// import static net.bytebuddy.matcher.ElementMatchers.nameStartsWithIgnoreCase;
// import static net.bytebuddy.matcher.ElementMatchers.not;
//
// public class TraceAgent {
//
//     public static void premain(String arguments, Instrumentation instrumentation) {
//         new AgentBuilder.Default()
//                 .type(buildMatch())
//                 .transform((builder, type, classLoader, module) ->
//                         builder.method(ElementMatchers.any())
//                                 .intercept(MethodDelegation.to(TraceInterceptor.class)) // 拦截器
//                 ).installOn(instrumentation);
//     }
//
//     public static ElementMatcher<? super TypeDescription> buildMatch() {
//         ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
//             @Override
//             public boolean matches(NamedElement target) {
//                 return true;
//             }
//         };
//         judge = judge.and(not(isInterface())).and(not(isSetter()))
//                 .and(nameStartsWithIgnoreCase("io.spring"))
//                 .and(not(nameContainsIgnoreCase("util")))
//                 .and(not(nameContainsIgnoreCase("interceptor")));
//         judge = judge.and(not(isSetter()));
//         return new ProtectiveShieldMatcher(judge);
//     }
//
// }
