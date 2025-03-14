package org.tracing.core.plugin.point.impl;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ControllerInterceptorPoint implements MethodInterceptorPoint {
    @Override
    public ElementMatcher<MethodDescription> getMethodMatcher() {
        return named("test");
    }

    @Override
    public String getInterceptor() {
        return "interceptor.ControllerInterceptor";
    }

    @Override
    public boolean isOverrideArgs() {
        return false;
    }
}
