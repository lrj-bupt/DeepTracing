package org.tracing.core.context.span;

import org.tracing.core.context.tag.AbstractTag;
import org.tracing.component.Component;
import org.tracing.core.context.TraceSegment;
import org.tracing.network.language.tracing.SpanObject;
import org.tracing.network.language.tracing.SpanType;

public abstract class AbstractSpan {
    protected long startTime;
    protected long endTime;
    protected SpanLayer layer;
    protected String operationName;
    public boolean finish(TraceSegment owner){
        this.endTime = System.currentTimeMillis();
        owner.archive(this);  // 归档
        return true;
    }

    abstract public int getSpanId();

    public AbstractSpan start(){
        this.startTime = System.currentTimeMillis();
        return this;
    }

    public void setLayer(SpanLayer layer){
        this.layer = layer;
    }

    abstract public AbstractSpan setComponent(Component component);

    abstract public String getOperationName();

    abstract public AbstractSpan setOperationName(String operationName);

    public abstract AbstractSpan tag(AbstractTag tag, String value);

    public abstract boolean isEntry();

    abstract boolean isExit();

    abstract public SpanObject.Builder transform();
}
