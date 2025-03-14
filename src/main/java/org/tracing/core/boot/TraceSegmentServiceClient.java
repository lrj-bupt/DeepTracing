package org.tracing.core.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.tracing.commons.DataCarrier;
import org.tracing.commons.consumer.IConsumer;
import org.tracing.config.Config;
import org.tracing.core.context.TraceSegment;
import org.tracing.core.context.TracingContext;
import org.tracing.core.context.TracingContextListener;
import org.tracing.network.language.tracing.SegmentObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TraceSegmentServiceClient implements BootService, IConsumer<TraceSegment>, TracingContextListener {
    DataCarrier<TraceSegment> carrier;
    Channel channel;
    ObjectMapper mapper;

    String SERVER_ADDR = "localhost";
    String QUEUE_NAME = "trace_segment";
    @Override
    public void prepare() {
        mapper = new ObjectMapper();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDR);
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void boot() {
        carrier = new DataCarrier<>(Config.Buffer.CHANNEL_SIZE, Config.Buffer.BUFFER_SIZE);
        carrier.consume(this,1);  // 创建ConsumerThread执行自身consumer方法
    }

    @Override
    public void onComplete() {
        TracingContext.ListenerManager.add(this);  // 注册自身 未来回调
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

    // 发送数据到后端
    @Override
    public void consume(List<TraceSegment> data) {
        if(data == null || data.isEmpty()) {
            return;
        }
        for (TraceSegment segment : data) {
            try {
                SegmentObject value = segment.transform();
                channel.basicPublish("",QUEUE_NAME,null, value.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterFinished(TraceSegment segment) {
        // TracingContext调用finish方法 将数据存入
        carrier.produce(segment);
    }
}
