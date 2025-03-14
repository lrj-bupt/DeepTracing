package org.tracing.commons.partition;

public interface IDataPartitioner {
    public int partition(int total);

    public int maxRetryCount();
}
