package org.tracing.core.plugin.point.impl;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.point.AnnotationTypeMatch;
import org.tracing.core.plugin.point.ArgumentTypeNameMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class AnnoInterceptorPoint implements MethodInterceptorPoint {
    private String targetName = null;
    public AnnoInterceptorPoint(String targetName){
        this.targetName = targetName;
    }
    public ElementMatcher<MethodDescription> getMethodMatcher(){
        return named(targetName)
                .and(ArgumentTypeNameMatch.takesArgumentWithType(0, "java.lang.String"))
                .and(AnnotationTypeMatch.isAnnotationPresent("test.Test"));
    }

    @Override
    public String getInterceptor() {
        return null;
    }

    @Override
    public boolean isOverrideArgs() {
        return false;
    }
}
