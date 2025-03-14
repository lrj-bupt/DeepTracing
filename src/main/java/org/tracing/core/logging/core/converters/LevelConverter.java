package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

public class LevelConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getLevel().name();
    }

    @Override
    public String getKey() {
        return "level";
    }
}
