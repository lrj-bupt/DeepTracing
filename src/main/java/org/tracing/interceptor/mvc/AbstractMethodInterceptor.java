package org.tracing.interceptor.mvc;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.tracing.plugins.mvc.common.Constants;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.context.RuntimeContext;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.tracing.plugins.mvc.common.EnhanceRequireObjectCache;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.plugin.MethodInterceptResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public abstract class AbstractMethodInterceptor implements MethodInterceptor {
    public abstract String getRequestURL(Method method);

    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        DispatcherServlet dispatcherServlet = (DispatcherServlet) obj;
        HandlerMethod handlerMethod = (HandlerMethod) args[0];
        RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) obj;
        Object request = ContextManger.getRuntimeContext().get(Constants.REQUEST_KEY_IN_RUNTIME_CONTEXT);  // 可以来自外层拦截器的设置
        if(request != null) {
            final ContextCarrier contextCarrier = new ContextCarrier();
            if(HttpServletRequest.class.isAssignableFrom(request.getClass())) {
                final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String operationName = this.buildOperationName(method, httpServletRequest.getMethod(), (EnhanceRequireObjectCache) obj.getDynamicField());

                AbstractSpan entrySpan = ContextManger.createEntrySpan(operationName, contextCarrier);

            }else if(jakarta.servlet.http.HttpServletRequest.class.isAssignableFrom(request.getClass())) {

            }
        }

        ContextCarrier contextCarrier = new ContextCarrier();

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        RuntimeContext runtimeContext = ContextManger.getRuntimeContext();
        Object request = runtimeContext.get(Constants.REQUEST_KEY_IN_RUNTIME_CONTEXT);
        if(request != null) {

        }
        ContextManger.stopSpan();

        return ret;
    }

    private String buildOperationName(Method method, String httpMethod, EnhanceRequireObjectCache pathMappingCache) {
        String operationName = null;
        String pathMapping = pathMappingCache.findPathMapping(method);
        if(pathMapping == null) {

        }
        return operationName;
    }
}
