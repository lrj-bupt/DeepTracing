package org.tracing.interceptor.rabbitmq;

import com.rabbitmq.client.AMQP;
import org.tracing.core.context.CarrierItem;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.context.tag.Tags;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RabbitMQProducerInterceptor implements MethodInterceptor {
    public static final String OPERATE_NAME_PREFIX = "RabbitMQ/";
    public static final String PRODUCER_OPERATE_NAME_SUFFIX = "/Producer";
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        ContextCarrier contextCarrier = new ContextCarrier();
        AMQP.BasicProperties properties = (AMQP.BasicProperties) args[4];
        AMQP.BasicProperties.Builder propertiesBuilder;

        Map<String, Object> headers = new HashMap<String, Object>();
        if(properties != null){
            propertiesBuilder = properties.builder().appId(properties.getAppId()).clusterId(properties.getClusterId())
                    .contentEncoding(properties.getContentEncoding())
                    .contentType(properties.getContentType())
                    .correlationId(properties.getCorrelationId())
                    .deliveryMode(properties.getDeliveryMode())
                    .expiration(properties.getExpiration())
                    .messageId(properties.getMessageId())
                    .priority(properties.getPriority())
                    .replyTo(properties.getReplyTo())
                    .timestamp(properties.getTimestamp())
                    .type(properties.getType())
                    .userId(properties.getUserId());
            if (properties.getHeaders() != null) {
                headers.putAll(properties.getHeaders());
            }
        }else {
            propertiesBuilder = new AMQP.BasicProperties.Builder();
        }
        String exChangeName = (String) args[0];
        String queueName = (String) args[1];
        String url = (String) obj.getDynamicField();
        AbstractSpan activeSpan = ContextManger.createExitSpan(OPERATE_NAME_PREFIX+"Topic/" + exChangeName+"Queue/" + queueName+PRODUCER_OPERATE_NAME_SUFFIX,contextCarrier,url);
        Tags.MQ_BREAK.set(activeSpan, url);
        Tags.MQ_QUEUE.set(activeSpan, queueName);
        Tags.MQ_TOPIC.set(activeSpan, exChangeName);

        CarrierItem next = contextCarrier.items();

        while(next.hasNext()){
            next = next.next();
            headers.put(next.getHeadKey(), next.getHeadValue());
        }
        args[4] = propertiesBuilder.headers(headers).build();
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        ContextManger.stopSpan();
        return ret;
    }
}
