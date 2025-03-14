package org.tracing.core.context.tag;

import org.tracing.core.context.span.AbstractSpan;

public class StringTag extends AbstractTag{

    public StringTag(String key){
        super(key);
    }

    public void set(AbstractSpan span, String value){
        span.tag(this, value);
    }
}
