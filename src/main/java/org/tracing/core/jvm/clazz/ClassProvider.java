package org.tracing.core.jvm.clazz;

import org.tracing.network.language.metric.Class;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

public enum ClassProvider {
    INSTANCE;
    private final ClassLoadingMXBean classLoadingMXBean;
    ClassProvider() {
        this.classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
    }

    public Class getClassMetric() {
        Class.Builder builder = Class.newBuilder();
        builder.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());  // 如果被卸载 会减小
        builder.setTotalUnloadedClassCount(classLoadingMXBean.getUnloadedClassCount());
        builder.setTotalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount());
        return builder.build();
    }
}
