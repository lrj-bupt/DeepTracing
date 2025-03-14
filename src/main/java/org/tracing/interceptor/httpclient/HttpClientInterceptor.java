package org.tracing.interceptor.httpclient;

import org.tracing.component.ComponentDefine;
import org.tracing.core.context.CarrierItem;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.plugin.MethodInterceptResult;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;

public class HttpClientInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        ServletRequestAttributes n;
        HandlerMethod m = null;
        RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) obj;
        HttpMethod httpMethod = (HttpMethod) args[0];
        final ContextCarrier carrier;
        if(httpMethod == null){
            return;
        }
        try {
            final String remotePeer = httpMethod.getURI().getHost() + ":" + httpMethod.getURI().getPort();
            final URI uri = httpMethod.getURI();
            String requestURI = getRequestURI(uri);
            carrier = new ContextCarrier();
            AbstractSpan exitSpan = ContextManger.createExitSpan(requestURI, carrier, remotePeer);

            exitSpan.setComponent(ComponentDefine.HTTP_CLIENT);

            for(CarrierItem next = carrier.items(); next.hasNext();){
                next = next.next();
                httpMethod.setRequestHeader(next.getHeadKey(), next.getHeadValue());
            }
        } catch (URIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        if(ret != null) {
            int statusCode = (Integer) ret;
            AbstractSpan abstractSpan = ContextManger.activeSpan();

        }
        ContextManger.stopSpan();
        return ret;
    }

    private String getRequestURI(URI uri) throws URIException {
        String path = uri.getPath();
        return path != null && path.length() > 0 ? path : "/";
    }

    private void collectHttpParam(HttpMethod httpMethod, AbstractSpan span){
        String queryString = httpMethod.getQueryString();
        if(queryString == null || queryString.length() == 0){

        }
    }
}
