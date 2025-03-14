package org.tracing.core.jvm.gc;

import org.tracing.network.language.metric.GC;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public enum GCProvider {
    INSTANCE;
    private GCMetricAccessor metricAccessor;
    private List<GarbageCollectorMXBean> beans;

    GCProvider() {
        beans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : beans) {
            String name = bean.getName();
            GCMetricAccessor accessor = findByBeanName(name);
            if (accessor != null) {
                metricAccessor = accessor;
                break;
            }
        }
    }

    public List<GC> getGCList() {
        return metricAccessor.getGCList();
    }

    private GCMetricAccessor findByBeanName(String name) {
        if (name.indexOf("PS") > -1) {
            //Parallel (Old) collector ( -XX:+UseParallelOldGC )
            return new ParallelGCModule(beans);
        } else if (name.indexOf("ConcurrentMarkSweep") > -1) {
            // CMS collector ( -XX:+UseConcMarkSweepGC )
            return new CMSGCModule(beans);
        } else if (name.indexOf("G1") > -1) {
            // G1 collector ( -XX:+UseG1GC )
            return new G1GCModule(beans);
        } else if (name.equals("MarkSweepCompact")) {
            // Serial collector ( -XX:+UseSerialGC )
            return new SerialGCModule(beans);
        } else if (name.indexOf("ZGC") > -1) {
            // Z garbage collector ( -XX:+UseZGC )
            return new ZGCModule(beans);
        } else {
            // Unknown
            return null;
        }
    }
}
