package org.tracing.commons.buffer;

import org.tracing.commons.AtomicRangeInteger;

import java.util.List;

public class Buffer<T> implements QueueBuffer<T> {
    private final Object[] buffer;
    private BufferStrategy strategy;
    private AtomicRangeInteger index;

    Buffer(int size) {
        buffer = new Object[size];
        index = new AtomicRangeInteger(0, size);
    }

    public boolean save(T data) {
        int i = index.getAndIncrement();
        if (buffer[i] != null) {  // 已经有数据了，选择策略
            switch (strategy) {
                case IF_POSSIBLE:
                    return false;
                default:
            }
        }
        buffer[i] = data;
        return true;
    }

    @Override
    public void obtain(List<T> consumerList) {
        int end = buffer.length;
        for (int i = 0; i < end; i++) {
            if (buffer[i] != null) {
                consumerList.add((T) buffer[i]);
                buffer[i] = null;
            }
        }
    }

    @Override
    public int getBufSize() {
        return buffer.length;
    }

    void obtain(List<T> consumeList, int start, int end) {
        for (int i = start; i < end; i++) {
            if (buffer[i] != null) {
                consumeList.add((T) buffer[i]);
                buffer[i] = null;
            }
        }
    }
}
