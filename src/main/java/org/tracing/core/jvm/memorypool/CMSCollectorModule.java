package org.tracing.core.jvm.memorypool;

import java.lang.management.MemoryPoolMXBean;
import java.util.List;

public class CMSCollectorModule extends MemoryPoolModule{
    public CMSCollectorModule(List<MemoryPoolMXBean> beans) {
        super(beans);
    }

    protected String[] getPermNames() {
        return new String[] {"CMS Perm Gen", "Compressed Class Space"};
    }

    protected String[] getCodeCacheNames() {
        return new String[] {"Code Cache"};
    }

    protected String[] getEdenNames() {
        return new String[] {"Par Eden Space"};
    }

    protected String[] getOldNames() {
        return new String[] {"CMS Old Gen"};
    }

    protected String[] getSurvivorNames() {
        return new String[] {"Par Survivor Space"};
    }

    protected String[] getMetaspaceNames() {
        return new String[] {"Metaspace"};
    }
}
