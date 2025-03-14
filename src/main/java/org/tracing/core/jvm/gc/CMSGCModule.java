package org.tracing.core.jvm.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class CMSGCModule extends GCModule{
    @Override
    protected String getOldGCName() {
        return null;
    }

    public CMSGCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override
    protected String getNewGCName() {
        return null;
    }
}
