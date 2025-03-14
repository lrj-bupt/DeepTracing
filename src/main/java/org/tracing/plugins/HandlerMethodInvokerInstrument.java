package org.tracing.plugins;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.NameMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.PlugDefine;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HandlerMethodInvokerInstrument extends PlugDefine {
    @Override
    public ClassMatch enhanceClassName() {
        return new NameMatch("org.springframework.web.bind.annotation.support.HandlerMethodInvoker");
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{
                new MethodInterceptorPoint() {

                    @Override
                    public ElementMatcher<MethodDescription> getMethodMatcher() {
                        return named("invokeHandlerMethod");
                    }

                    @Override
                    public String getInterceptor() {
                        return "interceptor.mvc.HandlerMethodInvokerInterceptor";
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    public MethodInterceptorPoint[] getStaticMethodInterceptorPoint() {
        return null;
    }

    @Override
    public MethodInterceptorPoint[] getConstructInterceptorPoint() {
        return null;
    }
}
