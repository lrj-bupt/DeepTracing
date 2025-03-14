package org.tracing.commons.partition;

public class SampleRollingPartitioner implements IDataPartitioner{
    private volatile int i = 0;
    @Override
    public int partition(int total) {
        return Math.abs(i++ % total);
    }

    @Override
    public int maxRetryCount() {
        return 3;
    }
}
