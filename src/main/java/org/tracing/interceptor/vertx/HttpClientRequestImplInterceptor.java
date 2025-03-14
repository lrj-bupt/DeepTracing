package org.tracing.interceptor.vertx;

import org.tracing.core.context.CarrierItem;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import io.vertx.core.http.HttpClientRequest;

import java.lang.reflect.Method;

public class HttpClientRequestImplInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        HttpClientRequest request = (HttpClientRequest) obj;
        ContextCarrier carrier = new ContextCarrier();
        AbstractSpan span = ContextManger.createExitSpan(toPath(request.uri()), carrier,"");
        CarrierItem items = carrier.items();
        while (items.hasNext()) {
            items = items.next();
            request.headers().add(items.getHeadKey(), items.getHeadValue());
        }
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return null;
    }

    private static String toPath(String uri) {
        int index = uri.indexOf("?");
        if (index > -1) {
            return uri.substring(0, index);
        } else {
            return uri;
        }
    }
}
