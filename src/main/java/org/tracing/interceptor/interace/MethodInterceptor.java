package org.tracing.interceptor.interace;

import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.EnhanceInstance;

import java.lang.reflect.Method;

public interface MethodInterceptor {
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result);
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret);
}
