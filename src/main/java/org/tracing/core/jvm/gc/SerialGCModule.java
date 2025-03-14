package org.tracing.core.jvm.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class SerialGCModule extends GCModule{
    public SerialGCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override
    protected String getOldGCName() {
        return null;
    }

    @Override
    protected String getNewGCName() {
        return null;
    }
}
