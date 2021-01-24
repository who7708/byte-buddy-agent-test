package com.maggie.measure.tracecontext;


import java.util.Stack;

public class TraceContext {
    private static final InheritableThreadLocal<Stack<Span>> TRACE_CONTEXT = new InheritableThreadLocal<Stack<Span>>();

    public static void push(Span span) {
        Stack<Span> stack = TRACE_CONTEXT.get();
        if (stack == null) {
            stack = new Stack<>();
        }
        stack.push(span);
        TRACE_CONTEXT.set(stack);
    }

    public static Span getSpan() {
        Stack<Span> stack = TRACE_CONTEXT.get();
        if (stack == null) {
            return null;
        }
        return stack.peek();
    }
}
