package org.tracing.core.jvm.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class G1GCModule extends GCModule{
    @Override
    protected String getOldGCName() {
        return null;
    }

    @Override
    protected String getNewGCName() {
        return null;
    }

    public G1GCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }
}
