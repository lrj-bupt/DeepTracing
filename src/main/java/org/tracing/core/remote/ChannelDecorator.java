package org.tracing.core.remote;

import io.grpc.Channel;

public interface ChannelDecorator {
    Channel build(Channel channel);
}
