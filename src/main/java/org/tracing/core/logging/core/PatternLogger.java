package org.tracing.core.logging.core;

public class PatternLogger extends AbstractLogger{
    private String pattern;
    public PatternLogger(Class<?> clazz, String pattern) {
        super(clazz.getSimpleName());
        this.setPattern(pattern);
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        this.converters = new Parser(pattern, DEFAULT_CONVERTER_MAP).parse();
    }

    protected String format(LogLevel level, String message, Throwable t) {
        LogEvent logEvent = new LogEvent(level, message, t, targetClass);
        StringBuilder stringBuilder = new StringBuilder();
        for (Converter converter : this.converters) {
            stringBuilder.append(converter.convert(logEvent));
        }

        return stringBuilder.toString();

    }

    public void info(String message, Object... objects) {

    }

    public void error(Throwable throwable, String message, Object... objects) {

    }

    public void error(String message) {
        this.logger(LogLevel.ERROR, message, null);
    }

    public void error(Throwable throwable, String message) {
        this.logger(LogLevel.ERROR, message, throwable);
    }

    protected void logger(LogLevel level, String message, Throwable e) {
        WriterFactory.getLogWriter();
    }
}
