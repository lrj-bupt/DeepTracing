package org.tracing.commons.consumer;

import java.util.List;

public interface IConsumer<T> {
    void consume(List<T> data);
}
