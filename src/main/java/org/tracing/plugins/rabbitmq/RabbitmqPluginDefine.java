package org.tracing.plugins.rabbitmq;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.point.ArgumentTypeNameMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.core.plugin.PlugDefine;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class RabbitmqPluginDefine extends PlugDefine {
    public static final String INTERCEPTOR_CLASS = "interceptor.rabbitmq.RabbitMQProducerInterceptor";
    public static final String CONSUMER_INTERCEPTOR_CLASS = "interceptor.rabbitmq.RabbitMQConsumerInterceptor";
    public static final String PUBLISH_ENHANCE_METHOD = "basicPublish";
    public static final String CONSUMER_ENHANCE_METHOD = "basicConsumer";
    @Override
    public ClassMatch enhanceClassName() {
        return null;
    }

    @Override
    public MethodInterceptorPoint[] getInstanceMethodInterceptorPoints() {
        return new MethodInterceptorPoint[]{
                new MethodInterceptorPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodMatcher() {
                        return named(PUBLISH_ENHANCE_METHOD).and(ArgumentTypeNameMatch.takesArgumentWithType(4,"com.rabbitmq.client.AMQP$BasicProperties"));
                    }

                    @Override
                    public String getInterceptor() {
                        return INTERCEPTOR_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return true;
                    }
                },
                new MethodInterceptorPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodMatcher() {
                        return named(CONSUMER_ENHANCE_METHOD).and(takesArguments(7));
                    }

                    @Override
                    public String getInterceptor() {
                        return CONSUMER_INTERCEPTOR_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return true;
                    }
                }
        };
    }

    @Override
    public MethodInterceptorPoint[] getStaticMethodInterceptorPoint() {
        return null;
    }

    @Override
    public MethodInterceptorPoint[] getConstructInterceptorPoint() {
        return null;
    }
}
