package org.tracing.commons.buffer;

import java.util.List;

public interface QueueBuffer<T> {
    boolean save(T data);

    void obtain(List<T> consumerList);

    int getBufSize();
}
