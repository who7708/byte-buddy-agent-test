package com.maggie.measure.intercept;

import com.maggie.measure.tracecontext.Span;
import com.maggie.measure.tracecontext.TraceContext;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.apache.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MyHttpAdvice {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @AllArguments Object[] allArguments,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("start = " + start);
        Span span = TraceContext.getSpan();
        try {
            for (Object allArgument : allArguments) {
                if (allArgument instanceof HttpRequest) {
                    HttpRequest req = (HttpRequest) allArgument;
                    if (span != null) {
                        req.setHeader("id", span.getId());
                        req.setHeader("seq", (Integer.parseInt(span.getSeq()) + 1) + "");
                    }
                }
            }
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println("AGENT 拦截 HTTP 花费时间:" + (System.currentTimeMillis() - start) + "ms");
        }

    }
}