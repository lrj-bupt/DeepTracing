package org.tracing.commons;

import org.tracing.commons.buffer.Channels;
import org.tracing.commons.consumer.ConsumerDriver;
import org.tracing.commons.consumer.IConsumer;
import org.tracing.commons.partition.SampleRollingPartitioner;

public class DataCarrier<T> {
    private Channels<T> channels;  // 保存Buffer数组并进行负载均衡
    private ConsumerDriver driver;

    public DataCarrier(int channelSize, int bufferSize) {
        channels = new Channels<>(channelSize, bufferSize, new SampleRollingPartitioner());
    }

    public void consume(IConsumer<T> consumer, int num) {
        this.consume(consumer, num, 20);
    }

    public void consume(IConsumer<T> consumer, int num, long consumerCycle) {
        if (driver != null) {
            driver.close();
        }
        driver = new ConsumerDriver<>(this.channels, consumer, num);
        driver.begin();
    }

    public boolean produce(T data) {
        return this.channels.save(data);
    }
}
