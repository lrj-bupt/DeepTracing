package org.tracing.core.logging.core;

import org.tracing.config.Config;

public class PatternLogResolver {
    public PatternLogger getLogger(Class<?> clazz) {
        return new PatternLogger(clazz, Config.Logging.PATTERN);
    }
}
