package org.tracing.plugins.vertx;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.NameMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.PlugDefine;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class HttpClientRequestImplInstrumentation extends PlugDefine {
    @Override
    public ClassMatch enhanceClassName() {
        return new NameMatch("io.vertx.core.http.impl.HttpClientRequestImpl");
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{
                new MethodInterceptorPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodMatcher() {
                        return named("end").or(named("sendHead")).and(takesArguments(1));
                    }

                    @Override
                    public String getInterceptor() {
                        return "interceptor.vertx.HttpClientRequestImplInterceptor";
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
