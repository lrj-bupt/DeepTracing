package org.tracing.commons.partition;

public class ProducerThreadPartitioner implements IDataPartitioner{
    @Override
    public int partition(int total) {
        return (int) Thread.currentThread().getId() % total;
    }

    @Override
    public int maxRetryCount() {
        return 1;
    }
}
