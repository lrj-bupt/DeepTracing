package org.tracing.commons.buffer;

import org.tracing.commons.partition.IDataPartitioner;

public class Channels<T> {
    private final long size;
    private final QueueBuffer<T>[] bufferChannels;
    private IDataPartitioner dataPartitioner;

    public Channels(int channelSize, int bufferSize, IDataPartitioner partitioner) {
        this.dataPartitioner = partitioner;
        bufferChannels = new QueueBuffer[channelSize];
        for (int i = 0; i < channelSize; i++) {
            bufferChannels[i] = new Buffer<>(bufferSize);
        }

        size = (long) channelSize * bufferSize;
    }

    // 选择一个环形数组存储数据
    public boolean save(T data) {
        int index = dataPartitioner.partition(bufferChannels.length);
        int retryCount = 1;
        retryCount = dataPartitioner.maxRetryCount() > 1 ? dataPartitioner.maxRetryCount() : retryCount;
        for (; retryCount > 0; retryCount--) {
            bufferChannels[index].save(data);
            return true;
        }

        return false;
    }

    public int getChannelSize() {
        return bufferChannels.length;
    }

    public long size() {
        return size;
    }

    public QueueBuffer<T> getBuffer(int index) {
        return bufferChannels[index];
    }
}
