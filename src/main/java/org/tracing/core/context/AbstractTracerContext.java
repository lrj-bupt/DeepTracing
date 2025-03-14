package org.tracing.core.context;

import org.tracing.core.context.span.AbstractSpan;

public interface AbstractTracerContext {
    void inject(ContextCarrier ctx);
    void inject(AbstractSpan span, ContextCarrier ctx);
    void extract(ContextCarrier ctx);
    String getTraceId();
    int getSpanId();
    AbstractSpan createEntrySpan(String operationName);
    AbstractSpan createExitSpan(String operationName, String remotePeer);

    AbstractSpan getActiveSpan();

    public boolean stopSpan(AbstractSpan span);
}
