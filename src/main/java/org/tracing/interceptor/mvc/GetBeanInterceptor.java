package org.tracing.interceptor.mvc;

import org.tracing.plugins.mvc.common.Constants;
import org.tracing.core.context.ContextManger;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class GetBeanInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        ContextManger.getRuntimeContext().put(Constants.REQUEST_KEY_IN_RUNTIME_CONTEXT, ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());
        return null;
    }
}
