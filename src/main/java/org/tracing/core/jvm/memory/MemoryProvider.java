package org.tracing.core.jvm.memory;

import org.tracing.network.language.metric.Memory;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.LinkedList;
import java.util.List;

public enum MemoryProvider {
    INSTANCE;
    private final MemoryMXBean memoryMXBean;

    MemoryProvider() {
        this.memoryMXBean = java.lang.management.ManagementFactory.getMemoryMXBean();
    }

    public List<Memory> getMemoryMetricsList() {
        List<Memory> memoryList = new LinkedList<>();

        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Memory.Builder builder = Memory.newBuilder();
        memoryList.add(builder
                .setInit(heapMemoryUsage.getInit())
                .setMax(heapMemoryUsage.getMax())
                .setCommitted(heapMemoryUsage.getCommitted())
                .setUsed(heapMemoryUsage.getUsed())
                .build());
        // 堆外内存包括 代码堆、元空间、压缩类空间
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        memoryList.add(builder
                .setInit(nonHeapMemoryUsage.getInit())
                .setMax(nonHeapMemoryUsage.getMax())
                .setCommitted(nonHeapMemoryUsage.getCommitted())
                .setUsed(nonHeapMemoryUsage.getUsed())
                .build());

        return memoryList;
    }
}
