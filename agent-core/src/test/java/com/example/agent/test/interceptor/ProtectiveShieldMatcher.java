package com.example.agent.test.interceptor;

import net.bytebuddy.matcher.ElementMatcher;

public class ProtectiveShieldMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {

    private final ElementMatcher<? super T> matcher;

    public ProtectiveShieldMatcher(ElementMatcher<? super T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(T target) {
        try {
            return this.matcher.matches(target);
        } catch (Throwable t) {
            //logger.warn(t, "Byte-buddy occurs exception when match type.");
            return false;
        }
    }
}
