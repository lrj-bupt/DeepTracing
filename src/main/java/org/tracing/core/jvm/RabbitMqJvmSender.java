package org.tracing.core.jvm;

import org.tracing.core.boot.BootService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.tracing.config.Config;
import org.tracing.network.language.metric.JVMMetric;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;

public class RabbitMqJvmSender implements BootService, Runnable {
    String QUEUE_NAME = "jvm-metrics";
    Channel channel;
    BlockingQueue<JVMMetric> queue;
    ConnectionFactory factory;

    @Override
    public void prepare() {
        queue = new LinkedBlockingDeque<>(Config.Jvm.BUFFER_SIZE);
    }

    @Override
    public void boot() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        queue = new LinkedBlockingDeque<>(Config.Jvm.BUFFER_SIZE);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void shutdown() {
        if(this.channel != null && this.channel.isOpen()) {
            try {
                this.channel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        LinkedList<JVMMetric> buffer = new LinkedList<>();
        queue.drainTo(buffer);
        if(buffer.size() > 0) {
            JVMMetricCollection.Builder builder = JVMMetricCollection.newBuilder();
            builder.addMetrics(buffer);
            builder.setServiceName(Config.SERVICE_NAME);
        }
    }

    public void offer(JVMMetric metric){
        if(!queue.offer(metric)){
            queue.poll();
            queue.offer(metric);
        }
    }
}
