package org.tracing.interceptor;

import org.tracing.interceptor.interace.MethodInterceptor;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class ControllerInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        System.out.println("before Controller Method");
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return ret;
    }
}
