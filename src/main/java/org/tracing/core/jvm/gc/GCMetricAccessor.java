package org.tracing.core.jvm.gc;

import org.tracing.network.language.metric.GC;

import java.util.List;

public interface GCMetricAccessor {
    List<GC> getGCList();
}
