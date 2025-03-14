package org.tracing.plugins;

import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.SignatureMatch;
import org.tracing.core.plugin.point.impl.ControllerInterceptorPoint;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.PlugDefine;

public class ClassAnnotationPluginDefine extends PlugDefine {
    @Override
    public ClassMatch enhanceClassName() {
        return new SignatureMatch("demo.Controller");
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{ new ControllerInterceptorPoint()};
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
