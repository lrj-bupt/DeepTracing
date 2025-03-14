package org.tracing.core.plugin.point.impl;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class ArgumentInterceptorPoint implements MethodInterceptorPoint {
    private String targetName = null;
    public ArgumentInterceptorPoint(String targetName){
        this.targetName = targetName;
    }
    public ElementMatcher<MethodDescription> getMethodMatcher(){
        return named(targetName).and(takesArguments(2)).and(takesArgument(0,named("java.lang.String")));
    }

    @Override
    public String getInterceptor() {
        return "";
    }

    @Override
    public boolean isOverrideArgs() {
        return false;
    }

}
