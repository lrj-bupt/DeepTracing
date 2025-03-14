package org.tracing.core.context;

import org.tracing.core.context.tag.AbstractTag;
import org.tracing.network.language.common.KeyStringValuePair;

public class TagValuePair {
    private AbstractTag key;
    private String value;

    public TagValuePair(AbstractTag key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public AbstractTag getKey() {
        return key;
    }

    public KeyStringValuePair transform() {
        KeyStringValuePair.Builder builder = KeyStringValuePair.newBuilder();
        builder.setKey(key.key());
        builder.setValue(value);
        return builder.build();
    }
}
