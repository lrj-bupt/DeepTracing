package org.tracing.interceptor.jdbccommons;

import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;

import java.lang.reflect.Method;

public class JDBCDriverInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        if(ret instanceof EnhanceInstance) {
            ((EnhanceInstance) ret).setSkyDynamicField(URLParser.parser((String) args[0]));
        }
        return ret;
    }
}
