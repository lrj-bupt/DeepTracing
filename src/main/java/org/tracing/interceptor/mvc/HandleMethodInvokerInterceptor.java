package org.tracing.interceptor.mvc;

import org.tracing.core.context.ContextManger;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.springframework.web.context.request.NativeWebRequest;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class HandleMethodInvokerInterceptor implements MethodInterceptor {

    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        ContextManger.getRuntimeContext().put("response", ((NativeWebRequest) args[2]).getNativeResponse());

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return ret;
    }
}
