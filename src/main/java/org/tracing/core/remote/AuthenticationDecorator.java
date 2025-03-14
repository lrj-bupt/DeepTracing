package org.tracing.core.remote;

import io.grpc.*;

public class AuthenticationDecorator implements ChannelDecorator{
    private static final Metadata.Key<String> AUTH_HEAD_HEADER_NAME = Metadata.Key.of("Authentication", Metadata.ASCII_STRING_MARSHALLER);
    @Override
    public Channel build(Channel channel){
        return ClientInterceptors.intercept(channel, new ClientInterceptor() {
            @Override
            public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
                return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
                    @Override
                    public void start(Listener<RespT> listener, Metadata headers) {
                        headers.put(AUTH_HEAD_HEADER_NAME, "");
                        super.start(listener, headers);
                    }
                };
            }
        });
    }
}
