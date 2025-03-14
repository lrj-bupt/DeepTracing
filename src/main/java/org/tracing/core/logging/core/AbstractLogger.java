package org.tracing.core.logging.core;

import org.tracing.core.logging.core.converters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class AbstractLogger {
    public static final Map<String, Class<? extends Converter>> DEFAULT_CONVERTER_MAP = new HashMap<>();

    protected final String targetClass;
    protected List<Converter> converters = new ArrayList<>();
    public AbstractLogger(String targetClass) {
        this.targetClass = targetClass;
    }

    static {
        DEFAULT_CONVERTER_MAP.put("thread", ThreadConverter.class);
        DEFAULT_CONVERTER_MAP.put("level", LevelConverter.class);
        DEFAULT_CONVERTER_MAP.put("date", DateConverter.class);
        DEFAULT_CONVERTER_MAP.put("message", MessageConverter.class);
        DEFAULT_CONVERTER_MAP.put("throwable", ThrowableConverter.class);
        DEFAULT_CONVERTER_MAP.put("class", ClassConverter.class);
    }

    private String replaceParam(String message, Object... params) {
        if(message == null) {
            return null;
        }
        int parametersIndex = 0;
        int startSize = 0;
        int index;
        String temMessage = message;
        while((index = message.indexOf("{}", startSize)) != -1) {
            if(parametersIndex >= params.length) {
                break;
            }
            // quote 防止转义字符
            temMessage = temMessage.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(params[parametersIndex++])));
            startSize = index + 2;
        }

        return temMessage;
    }

    private void logger(String message) {

    }
}
