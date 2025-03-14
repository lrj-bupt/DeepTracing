package org.tracing.core.context.tag;

import org.tracing.core.context.span.AbstractSpan;

public class IntegerTag extends AbstractTag{
    public IntegerTag(String key){
        super(key);
    }

    public void set(AbstractSpan span, Integer tagValue) {
        span.tag(this, Integer.toString(tagValue));
    }
}
