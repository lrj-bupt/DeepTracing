package org.tracing.core.context;

import org.tracing.network.language.tracing.SegmentReference;

public class TraceSegmentRef {
    private String traceId;
    private int spanId;
    private String parentService;
    private String traceSegmentId;

    public String getTraceId() {
        return traceId;
    }

    public int getSpanId() {
        return spanId;
    }

    public String getParentService() {
        return parentService;
    }

    public String getTraceSegmentId() {
        return traceSegmentId;
    }

    public TraceSegmentRef(ContextCarrier carrier){
        this.traceId = carrier.getTraceId();
        this.spanId = carrier.getSpanId();
        this.parentService = carrier.getParentService();
        this.traceSegmentId = carrier.getTraceSegmentId();
    }

    public SegmentReference transform() {
        SegmentReference.Builder refBuilder = SegmentReference.newBuilder();

        refBuilder.setTraceId(traceId);
        refBuilder.setParentTraceSegmentId(traceSegmentId);
        refBuilder.setParentSpanId(spanId);
        refBuilder.setParentService(parentService);

        return refBuilder.build();
    }

}
