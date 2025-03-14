package org.tracing.core.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionUtil {
    public static String toString(Map<String, String[]> map) {
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
