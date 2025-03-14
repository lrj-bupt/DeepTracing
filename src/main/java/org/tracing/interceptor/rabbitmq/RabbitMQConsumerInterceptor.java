package org.tracing.interceptor.rabbitmq;

import com.rabbitmq.client.Consumer;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class RabbitMQConsumerInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        Consumer consumer = (Consumer) args[6];
        args[6] = new TracerConsumer(consumer);
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return ret;
    }
}
