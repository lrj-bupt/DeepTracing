package org.tracing.core.context;

public interface TracingContextListener {
    void afterFinished(TraceSegment segment);
}
