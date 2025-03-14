package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

public class MessageConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getMessage();
    }

    @Override
    public String getKey() {
        return "message";
    }
}
