package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

public class ClassConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getTargetClass();
    }

    @Override
    public String getKey() {
        return "logger";
    }
}
