package org.tracing.core.context.tag;

public abstract class AbstractTag {
    protected final String key;
    public AbstractTag(String key) {
        this.key = key;
    }
    public String key() {
        return this.key;
    }
}
