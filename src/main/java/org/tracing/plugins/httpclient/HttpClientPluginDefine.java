package org.tracing.plugins.httpclient;

import org.tracing.core.plugin.point.impl.HttpInterceptorPoint;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.NameMatch;
import org.tracing.core.plugin.PlugDefine;

public class HttpClientPluginDefine extends PlugDefine {

    @Override
    public ClassMatch enhanceClassName() {
        return new NameMatch("demo.HttpUtils");
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{new HttpInterceptorPoint()};
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
