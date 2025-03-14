package org.tracing.core.jvm.memorypool;

import org.tracing.network.language.metric.MemoryPool;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

public enum MemoryPoolProvider {
    INSTANCE;
    private MemoryPoolMetricsAccessor metricAccessor;

    private List<MemoryPoolMXBean> beans;

    MemoryPoolProvider() {
        beans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean bean : beans) {
            String name = bean.getName();
            MemoryPoolMetricsAccessor accessor = findByBeanName(name);
            if (accessor != null) {
                metricAccessor = accessor;
                break;
            }
        }
    }

    public List<MemoryPool> getMemoryPoolMetricsList() {
        return metricAccessor.getMemoryPoolMetricsList();
    }


    private MemoryPoolMetricsAccessor findByBeanName(String name) {
        if(name.indexOf("PS") > -1) {
            return new ParallelCollectorModule(beans);
        }else if(name.indexOf("CMS") > -1) {
            return new CMSCollectorModule(beans);
        }else if(name.indexOf("G1") > -1) {
            return new G1CollectorModule(beans);
        }else if(name.indexOf("Survivor Space") > -1) {
            return new SerialCollectorModule(beans);
        }else {
            return null;
        }
    }
}
