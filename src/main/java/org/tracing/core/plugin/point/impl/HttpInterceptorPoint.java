package org.tracing.core.plugin.point.impl;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class HttpInterceptorPoint implements MethodInterceptorPoint {
    @Override
    public ElementMatcher<MethodDescription> getMethodMatcher() {
        return named("executeMethod")
                .and(takesArguments(1))
                .and(takesArgument(0, named("org.apache.commons.httpclient.HttpMethod")));
    }

    @Override
    public String getInterceptor() {
        return "interceptor.httpclient.HttpClientInterceptor";
    }

    @Override
    public boolean isOverrideArgs() {
        return false;
    }
}
