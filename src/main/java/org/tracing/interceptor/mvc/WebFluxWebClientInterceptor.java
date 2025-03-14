package org.tracing.interceptor.mvc;

import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.net.URI;

public class WebFluxWebClientInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        Mono<ClientResponse> ret1 = (Mono<ClientResponse>) ret;
        return ret1.subscriberContext().flatMap(ctx->{
            ClientRequest request = (ClientRequest) args[0];
            URI url = request.url();
            final String operationName = getRequestURIString(url);
            final String remotePeer = getIPAndPort(url);
            ContextManger.createExitSpan(operationName, remotePeer);

            final ContextCarrier contextCarrier = new ContextCarrier();
            return ret1.doOnSuccess(clientResponse -> {
                HttpStatus httpStatus = clientResponse.statusCode();
            });
        });
    }

    private String getRequestURIString(URI uri) {
        String requestPath = uri.getPath();
        return requestPath != null && requestPath.length() > 0 ? requestPath : "/";
    }

    // return ip:port
    private String getIPAndPort(URI uri) {
        return uri.getHost() + ":" + uri.getPort();
    }
}
