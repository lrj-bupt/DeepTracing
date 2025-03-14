package org.tracing.core.logging.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    private int pointer = 0;
    private final String pattern;
    private final int patternLength;
    private final Map<String, Class<? extends Converter>> convertMaps;
    public Parser(String pattern, Map<String, Class<? extends Converter>> convertMaps) {
        this.pattern = pattern;
        this.convertMaps = convertMaps;
        this.patternLength = pattern.length();
    }

    public List<Converter> parse() {
        List<Converter> patternConverters = new ArrayList<>();
        StringBuilder buf = new StringBuilder();

        return combineLiteral();
    }

    private List<Converter> combineLiteral() {
        List<Converter> patternConverters = new ArrayList<>();

        return patternConverters;
    }

    private void addConverter(StringBuilder buf, List<Converter> patternConverters, Class<? extends Converter> aClass) {
        if(buf.length() > 0) {
            String result = buf.toString();
            try {
                patternConverters.add(aClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            buf.setLength(0);
        }
    }
}
