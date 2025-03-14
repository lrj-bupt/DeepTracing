package org.tracing.core.logging.core;

public interface Converter {
    String convert(LogEvent logEvent);
    String getKey();
}
