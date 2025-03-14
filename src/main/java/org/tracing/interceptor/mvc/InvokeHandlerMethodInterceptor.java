package org.tracing.interceptor.mvc;

import org.tracing.core.context.ContextManger;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class InvokeHandlerMethodInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        ContextManger.getRuntimeContext().put("spring-response", args[1]);
        ContextManger.getRuntimeContext().put("spring-request", args[0]);
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return ret;
    }
}
