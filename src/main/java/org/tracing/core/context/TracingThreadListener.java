package org.tracing.core.context;

public interface TracingThreadListener {
    void afterMainThreadFinish(TracingContext tracingContext);
}
