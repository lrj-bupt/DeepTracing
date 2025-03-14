package org.tracing.core.jvm.cpu;

import org.tracing.network.language.common.CPU;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;


public class SunCpuAccessor {
    private final OperatingSystemMXBean osMxBean;
    public SunCpuAccessor(int cpuCoreNum) {
        osMxBean = ManagementFactory.getOperatingSystemMXBean();

    }

    public CPU.Builder getCpuMetric() {
        double load = osMxBean.getSystemLoadAverage();
        CPU.Builder builder = CPU.newBuilder();
        return builder.setUsagePercent(load);
    }
}
