package org.tracing.interceptor.rabbitmq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.tracing.component.ComponentDefine;
import org.tracing.core.context.CarrierItem;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.context.span.AbstractSpan;

import java.io.IOException;

public class TracerConsumer implements Consumer {

    private Consumer delegate;
    public TracerConsumer(Consumer delegate){
        this.delegate = delegate;
    }
    @Override
    public void handleConsumeOk(String consumerTag) {
        this.delegate.handleConsumeOk(consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        this.delegate.handleRecoverOk(consumerTag);
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        this.delegate.handleCancel(consumerTag);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException e) {
        this.delegate.handleShutdownSignal(consumerTag, e);
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        this.delegate.handleRecoverOk(consumerTag);
    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        ContextCarrier carrier = new ContextCarrier();
        AbstractSpan entrySpan = ContextManger.createEntrySpan("Rabbit/" + envelope.getExchange() + "Queue/" + envelope.getRoutingKey() + "/Consumer", null);

        entrySpan.setComponent(ComponentDefine.RABBITMQ_CONSUMER);

        CarrierItem next = carrier.items();
        while (next.hasNext()) {
            next = next.next();
            if (basicProperties.getHeaders() != null && basicProperties.getHeaders().get(next.getHeadKey()) != null) {
                next.setHeadValue(basicProperties.getHeaders().get(next.getHeadKey()).toString());
            }
        }

        ContextManger.extract(carrier);
        try {
            this.delegate.handleDelivery(s, envelope, basicProperties, bytes);
        }catch (Exception e){

        }finally {
            ContextManger.stopSpan();
        }
    }
}
