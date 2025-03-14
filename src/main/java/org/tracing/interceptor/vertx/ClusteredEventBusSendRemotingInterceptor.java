package org.tracing.interceptor.vertx;

import org.tracing.core.context.CarrierItem;
import org.tracing.core.context.ContextCarrier;
import org.tracing.core.context.ContextManger;
import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.interace.MethodInterceptor;
import io.vertx.core.eventbus.impl.clustered.ClusteredMessage;
import io.vertx.core.net.impl.ServerID;

import java.lang.reflect.Method;

// 拦截sendRemote方法
public class ClusteredEventBusSendRemotingInterceptor implements MethodInterceptor {
    @Override
    public void beforeMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, MethodInterceptResult result) {
        ClusteredMessage message = (ClusteredMessage) args[1];
        //
        ServerID sender = (ServerID) args[0];
        ContextCarrier carrier = new ContextCarrier();
        ContextManger.createExitSpan(message.address(), carrier,"");
        CarrierItem next = carrier.items();
        while (next.hasNext()){
            next = next.next();
            message.headers().add(next.getHeadKey(), next.getHeadValue());
        }
    }

    @Override
    public Object afterMethod(EnhanceInstance obj, Method method, Object[] args, Class<?>[] parameterTypes, Object ret) {
        return null;
    }
}
