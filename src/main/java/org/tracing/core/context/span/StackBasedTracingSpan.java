package org.tracing.core.context.span;

import org.tracing.component.Component;
import org.tracing.core.context.TagValuePair;
import org.tracing.core.context.tag.AbstractTag;
import org.tracing.core.context.AbstractTracerContext;
import org.tracing.core.context.TraceSegment;
import org.tracing.network.language.tracing.SpanObject;
import org.tracing.network.language.tracing.SpanType;

import java.util.ArrayList;
import java.util.List;

public abstract class StackBasedTracingSpan extends AbstractSpan{
    protected AbstractTracerContext owner;
    protected int stackDepth;
    protected int spanId;
    protected int parentSpanId;
    private String operationName;
    protected int componentId = 0;
    protected List<TagValuePair> tags;
    protected StackBasedTracingSpan(int spanId, int parentSpanId, String operationName, AbstractTracerContext owner){
        this.owner = owner;
        this.spanId = spanId;
        this.stackDepth = 0;
        this.parentSpanId = parentSpanId;
        this.operationName = operationName;
    }

    public int getSpanId() {
        return spanId;
    }

    public String getOperationName(){
        return operationName;
    }

    public AbstractSpan setOperationName(String operationName){
        this.operationName = operationName;
        return this;
    }

    public boolean finish(TraceSegment owner){
        if(--stackDepth == 0){
            return super.finish(owner);
        }else {
            return false;
        }
    }

    public StackBasedTracingSpan tag(AbstractTag tag, String value){
        if(tags == null){
            tags = new ArrayList(8);
        }
        TagValuePair tagValuePair = new TagValuePair(tag, value);
        tags.add(tagValuePair);
        return this;
    }

    public AbstractSpan setComponent(Component component) {
        this.componentId = component.getId();
        return this;
    }

    public SpanObject.Builder transform(){
        SpanObject.Builder builder = SpanObject.newBuilder();

        builder.setSpanId(getSpanId());
        builder.setParentSpanId(getSpanId());
        builder.setStartTime(startTime);
        builder.setEndTime(endTime);
        builder.setOperationName(operationName);
        if(isEntry()) {
            builder.setSpanType(SpanType.Entry);
        }else{
            builder.setSpanType(SpanType.Exit);
        }

        if(this.tags != null) {
            for (TagValuePair tag : this.tags) {
                builder.addTags(tag.transform());
            }
        }

        return builder;
    }
}
