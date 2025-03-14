package org.tracing.plugins.mvc;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.NameMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.PlugDefine;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HandlerMethodInstrumentation extends PlugDefine {
    public static final String ENHANCE_METHOD = "getBean";
    public static final String ENHANCE_CLASS = "org.springframework.web.method.HandlerMethod";
    @Override
    public ClassMatch enhanceClassName() {
        return NameMatch.byName(ENHANCE_CLASS);
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{
                new MethodInterceptorPoint() {

                    @Override
                    public ElementMatcher<MethodDescription> getMethodMatcher() {
                        return named(ENHANCE_METHOD);
                    }

                    @Override
                    public String getInterceptor() {
                        return "interceptor.mvc.GetBeanInterceptor";
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
        return new MethodInterceptorPoint[0];
    }

    @Override
    public MethodInterceptorPoint[] getConstructInterceptorPoint() {
        return new MethodInterceptorPoint[0];
    }
}
