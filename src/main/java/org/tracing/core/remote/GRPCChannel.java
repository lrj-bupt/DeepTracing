package org.tracing.core.remote;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

import java.util.LinkedList;
import java.util.List;

public class GRPCChannel {
    private final ManagedChannel originChannel;
    private final Channel channelWithDecorators;

    private GRPCChannel(String host, int port, List<ChannelDecorator> decorators){
        NettyChannelBuilder channelBuilder = NettyChannelBuilder.forAddress(host, port);
        this.originChannel = channelBuilder.build();
        Channel channel = originChannel;
        for (ChannelDecorator decorator : decorators) {
            channel = decorator.build(channel);
        }
        channelWithDecorators = channel;

    }

    public static Builder newBuilder(String host, int port){
        Builder builder = new Builder(host, port);
        return builder;
    }

    public static class Builder{
        private final String host;
        private final int port;
        private final List<ChannelDecorator> decorators;
        private Builder(String host, int port){
            this.host = host;
            this.port = port;
            this.decorators = new LinkedList<>();
        }

        public Builder addDecorator(ChannelDecorator decorator){
            decorators.add(decorator);
            return this;
        }

        public GRPCChannel build(){
            return new GRPCChannel(host, port, decorators);
        }
    }
}
