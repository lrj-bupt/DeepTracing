package org.tracing.core.logging.core;

public class LogEvent {
    private LogLevel level;
    private String message;
    private Throwable throwable;
    private String targetClass;

    public LogEvent(LogLevel level, String message, Throwable t, String targetClass) {
        this.level = level;
        this.message = message;
        this.throwable = t;
        this.targetClass = targetClass;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getTargetClass() {
        return targetClass;
    }
}
