package org.tracing.core.jvm.memorypool;

import org.tracing.network.language.metric.MemoryPool;

import java.util.List;

public interface MemoryPoolMetricsAccessor {
    List<MemoryPool> getMemoryPoolMetricsList();
}
