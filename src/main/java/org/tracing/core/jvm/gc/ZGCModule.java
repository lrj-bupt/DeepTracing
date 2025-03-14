package org.tracing.core.jvm.gc;

import org.tracing.network.language.metric.GC;
import org.tracing.network.language.metric.GCPhase;

import java.lang.management.GarbageCollectorMXBean;
import java.util.LinkedList;
import java.util.List;

public class ZGCModule implements GCMetricAccessor{
    private List<GarbageCollectorMXBean> beans;

    private long lastNormalGCCount = 0;
    private long lastNormalGCTime = 0;


    public ZGCModule(List<GarbageCollectorMXBean> beans) {
        this.beans = beans;
    }

    public List<GC> getGCList() {
        List<GC> gcList = new LinkedList<>();
        for (GarbageCollectorMXBean bean : beans) {
            long gcCount = 0;
            long gcTime = 0;
            String name = bean.getName();
            if(name.equals("ZGC")) {
                long collectionCount = bean.getCollectionCount();
                gcCount = collectionCount - lastNormalGCCount;

                long time = bean.getCollectionTime();
                gcTime = time - lastNormalGCTime;
                lastNormalGCTime = time;
            }else if(name.equals("ZGC Cycles")) {
                long collectionCount = bean.getCollectionCount();
                gcCount = collectionCount - lastNormalGCCount;
                lastNormalGCCount = collectionCount;
            }else if(name.equals("ZGC Pause")) {
                long time = bean.getCollectionTime();
                gcTime = time - lastNormalGCTime;
                lastNormalGCTime = time;
            }else {
                continue;
            }
            gcList.add(GC.newBuilder().setPhase(GCPhase.NORMAL).setCount(gcCount).setTime(gcTime).build());
        }

        return gcList;
    }
}
