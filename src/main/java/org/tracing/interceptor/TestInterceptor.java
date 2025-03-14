package org.tracing.interceptor;

import org.springframework.cache.annotation.EnableCaching;
import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.interace.MethodInterceptor;

import java.lang.reflect.Method;

public class TestInterceptor implements MethodInterceptor {
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result){
        System.out.println("before Method");
        String arg = (String) args[0];
        System.out.println("args: " + arg);
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return ret;
    }

}
