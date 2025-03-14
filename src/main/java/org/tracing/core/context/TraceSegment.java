package org.tracing.core.context;

import org.tracing.config.Config;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.context.ids.DistributeTraceId;
import org.tracing.core.context.ids.GlobalIdGenerator;
import org.tracing.network.language.tracing.SegmentObject;

import java.util.LinkedList;
import java.util.List;

public class TraceSegment {
    private String traceSegmentId;
    private TraceSegmentRef ref;
    private List<AbstractSpan> spans;
    private DistributeTraceId relatedGlobalTraceId;
    private final long createTime;

    public TraceSegment() {
        this.traceSegmentId = GlobalIdGenerator.generate();
        this.createTime = System.currentTimeMillis();
        this.spans = new LinkedList<>();
        this.relatedGlobalTraceId = new DistributeTraceId();
    }

    public void ref(TraceSegmentRef ref) {
        if (this.ref == null) {
            this.ref = ref;
        }
    }

    /**
     * After {@link AbstractSpan} is finished, archive it.
     */
    public void archive(AbstractSpan span) {
        spans.add(span);
    }

    public TraceSegmentRef getRef() {
        return ref;
    }

    public String getTraceSegmentId() {
        return traceSegmentId;
    }

    public DistributeTraceId getRelatedGlobalTraceId() {
        return relatedGlobalTraceId;
    }

    public void setRelatedGlobalTraceId(DistributeTraceId distributeTraceId) {
        this.relatedGlobalTraceId = distributeTraceId;
    }

    public SegmentObject transform() {
        SegmentObject.Builder segmentBuilder = SegmentObject.newBuilder();

        segmentBuilder.setTraceId(this.getRelatedGlobalTraceId().getId());
        segmentBuilder.setTraceSegmentId(this.traceSegmentId);

        for (AbstractSpan span : this.spans) {
            segmentBuilder.addSpans(span.transform());
        }
        segmentBuilder.setService(Config.SERVICE_NAME);
        segmentBuilder.setServiceInstance(Config.INSTANCE_NAME);
        return segmentBuilder.build();
    }
}
