// package com.example.agent.test.interceptor;
//
// import net.bytebuddy.implementation.bind.annotation.Origin;
// import net.bytebuddy.implementation.bind.annotation.RuntimeType;
// import net.bytebuddy.implementation.bind.annotation.SuperCall;
//
// import java.lang.reflect.Method;
// import java.util.List;
// import java.util.concurrent.Callable;
//
// public class TraceInterceptor {
//
//     @RuntimeType
//     public static Object intercept(@Origin Method method,
//                                    @SuperCall Callable<?> callable) throws Exception {
//         before(method);
//         try {
//             return callable.call();
//         } finally {
//             after();
//         }
//     }
//
//     public static void after() {
//         Trace trace = TraceWrapper.getTrace(); //Trace类，可自行实现，不是关键
//
//         if (null != trace) {
//             if (trace.callMethodSize() > 0) {
//
//                 CallMethod callMethod = trace.pop();
//
//                 if (null != callMethod && callMethod.isTraceFlag()) {
//
//                     callMethod.calculateCostTime();
//                     trace.addTraceRecord(new TraceRecord(callMethod));
//
//                 }
//
//                 if (trace.callMethodSize() == 0) {
//                     List<TraceRecord> traceRecordList = trace.getAllTraceRecord();
//                     if (null != traceRecordList && traceRecordList.size() > 0) {
//                         DBCollector collector = new DBCollector(traceRecordList);
//                         new Thread(collector).start();
//                         TraceWrapper.destory();
//                     }
//                 }
//             }
//         }
//     }
//
//     private static void before(Method method) {
//         Trace trace = TraceWrapper.getTrace();
//
//         CallMethod callMethod = new CallMethod(method);
//         if (isInnerClass(callMethod)) {    //spring中有很多内部类，可以去掉
//             callMethod.setTraceFlag(false);
//         } else {
//             callMethod.setTraceFlag(true);
//         }
//
//         //不管是否跟踪都放进去
//         trace.push(callMethod);
//     }
//
//     private static boolean isInnerClass(CallMethod callMethod) {
//         return callMethod.getClassName().indexOf('$') > -1;
//     }
//
// }
