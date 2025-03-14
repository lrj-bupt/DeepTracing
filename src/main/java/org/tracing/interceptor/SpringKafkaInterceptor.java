package org.tracing.interceptor;

import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.interace.MethodInterceptor;
import reactor.netty.http.client.HttpClient;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpringKafkaInterceptor<T> implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {

    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return null;
    }
}
