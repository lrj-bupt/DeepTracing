package org.tracing.plugins.mvc.common;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class PathMappingCache {
    private ConcurrentHashMap<Method, String> methodPathMapping = new ConcurrentHashMap<>();

    public PathMappingCache() {

    }

    public String findPathMapping(Method method) {
        return methodPathMapping.get(method);
    }

    public void addPathMapping(Method method, String methodPath) {
        methodPathMapping.put(method, methodPath);
    }
}
