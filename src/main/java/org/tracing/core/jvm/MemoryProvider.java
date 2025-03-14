package org.tracing.core.jvm;

import org.tracing.network.language.metric.Memory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.LinkedList;
import java.util.List;

public enum MemoryProvider {
    INSTANCE;
    private final MemoryMXBean memoryMXBean;

    MemoryProvider() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    public List<Memory> getMemoryMetricList() {
        List<Memory> memoryList = new LinkedList<>();

        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Memory.Builder heapMemoryBuilder = Memory.newBuilder();
        heapMemoryBuilder.setIsHeap(true);
        heapMemoryBuilder.setInit(heapMemoryUsage.getInit());
        heapMemoryBuilder.setUsed(heapMemoryUsage.getUsed());
        heapMemoryBuilder.setCommitted(heapMemoryUsage.getCommitted());
        heapMemoryBuilder.setMax(heapMemoryUsage.getMax());
        memoryList.add(heapMemoryBuilder.build());

        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        Memory.Builder nonHeapMemoryBuilder = Memory.newBuilder();
        nonHeapMemoryBuilder.setIsHeap(false);
        nonHeapMemoryBuilder.setInit(nonHeapMemoryUsage.getInit());
        nonHeapMemoryBuilder.setUsed(nonHeapMemoryUsage.getUsed());
        nonHeapMemoryBuilder.setCommitted(nonHeapMemoryUsage.getCommitted());
        nonHeapMemoryBuilder.setMax(nonHeapMemoryUsage.getMax());
        memoryList.add(nonHeapMemoryBuilder.build());

        return memoryList;
    }
}
