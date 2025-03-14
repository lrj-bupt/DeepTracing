package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

public class ThreadConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return Thread.currentThread().getName();
    }

    @Override
    public String getKey() {
        return "thread";
    }
}
