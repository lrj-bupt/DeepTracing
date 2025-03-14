package org.tracing.core.logging.api;

import org.tracing.core.logging.core.PatternLogger;
import org.tracing.core.logging.core.PatternLogResolver;

public class LogManager {
    private static PatternLogResolver resolver = new PatternLogResolver();

    public static PatternLogger getLogger(Class<?> clazz) {
        return resolver.getLogger(clazz);
    }
}
