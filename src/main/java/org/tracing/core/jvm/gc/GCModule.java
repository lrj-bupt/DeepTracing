package org.tracing.core.jvm.gc;

import org.tracing.network.language.metric.GC;
import org.tracing.network.language.metric.GCPhase;

import java.lang.management.GarbageCollectorMXBean;
import java.util.LinkedList;
import java.util.List;

public abstract class GCModule implements GCMetricAccessor{
    private List<GarbageCollectorMXBean> beans;

    private long lastOGCCount = 0;
    private long lastYGCCount = 0;
    private long lastOGCCollectionTime = 0;
    private long lastYGCCollectionTime = 0;

    public GCModule(List<GarbageCollectorMXBean> beans) {
        this.beans = beans;
    }


    @Override
    public List<GC> getGCList() {
        // 统计垃圾回收次数与回收时长
        List<GC> gcList = new LinkedList<GC>();
        for (GarbageCollectorMXBean bean : beans) {
            String name = bean.getName();
            GCPhase phase;
            long gcCount = 0;
            long gcTime = 0;
            if (name.equals(getNewGCName())) {
                phase = GCPhase.NEW;
                long collectionCount = bean.getCollectionCount();
                gcCount = collectionCount - lastYGCCount;
                lastYGCCount = collectionCount;

                long time = bean.getCollectionTime();
                gcTime = time - lastYGCCollectionTime;
                lastYGCCollectionTime = time;
            } else if (name.equals(getOldGCName())) {
                phase = GCPhase.OLD;
                long collectionCount = bean.getCollectionCount();
                gcCount = collectionCount - lastOGCCount;
                lastOGCCount = collectionCount;

                long time = bean.getCollectionTime();
                gcTime = time - lastOGCCollectionTime;
                lastOGCCollectionTime = time;
            } else {
                continue;
            }
            gcList.add(GC.newBuilder().setPhase(phase).setCount(gcCount).setTime(gcTime).build());

        }
        return gcList;
    }

    protected abstract String getOldGCName();

    protected abstract String getNewGCName();
}
