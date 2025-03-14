package org.tracing.commons.consumer;

import org.tracing.commons.buffer.Channels;

public class ConsumerDriver<T> {
    private boolean running;
    private Channels<T> channels;  // 是环形数组的数组
    private ConsumerThread[] consumerThreads;

    public ConsumerDriver(Channels<T> channels, IConsumer<T> consumer, int num) {
        this.channels = channels;
        consumerThreads = new ConsumerThread[num];
        for (int i = 0; i < num; i++) {
            consumerThreads[i] = new ConsumerThread<>("DataCarrier.Consumer." + i + "Thread", consumer);
            consumerThreads[i].setDaemon(true);
        }
    }

    public void begin() {
        if (running) {
            return;
        }
        this.allocateBuffer2Thread();
        for (ConsumerThread thread : consumerThreads) {
            thread.start();
        }
        running = true;
    }

    public void close() {
        this.running = false;

        for (ConsumerThread thread : consumerThreads) {
            thread.shutdown();
        }
    }

    // 将线程与数据源绑定
    private void allocateBuffer2Thread() {
        int channelSize = this.channels.getChannelSize();

        for (int channelIndex = 0; channelIndex < channelSize; channelIndex++) {
            int consumerIndex = channelIndex % consumerThreads.length;
            consumerThreads[consumerIndex].addDataSource(channels.getBuffer(channelIndex));
        }
    }

}
