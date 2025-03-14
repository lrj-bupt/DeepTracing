package org.tracing.core.plugin.point.interceptor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface MethodInterceptorPoint {
    public ElementMatcher<MethodDescription> getMethodMatcher();

    public String getInterceptor();

    public boolean isOverrideArgs();
}
