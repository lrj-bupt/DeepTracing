package org.tracing.core.logging.core.converters;

import org.tracing.core.logging.core.Converter;
import org.tracing.core.logging.core.LogEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter {

    @Override
    public String convert(LogEvent logEvent) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
    }

    @Override
    public String getKey() {
        return "@timestamp";
    }
}
