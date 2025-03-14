package org.tracing.core.jvm.cpu;

import org.tracing.network.language.common.CPU;

public enum CpuProvider {
    INSTANCE;
    private SunCpuAccessor sunCpuAccessor;
    CpuProvider() {
        int processorCount = Runtime.getRuntime().availableProcessors();
        this.sunCpuAccessor = new SunCpuAccessor(processorCount);
    }

    public CPU getCpuMetric() {
        return sunCpuAccessor.getCpuMetric().build();
    }
}
