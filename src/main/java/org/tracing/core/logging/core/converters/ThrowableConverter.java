package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

import java.io.ByteArrayOutputStream;

public class ThrowableConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        Throwable t = logEvent.getThrowable();
        return t == null ? "" : format(t);
    }

    public static String format(Throwable t) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // 自动刷新缓存区  Writer处理字符 Stream 处理字节
        t.printStackTrace(new java.io.PrintWriter(stream, true));
        String expMessage = stream.toString();
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expMessage;
    }

    @Override
    public String getKey() {
        return "throwable";
    }
}
