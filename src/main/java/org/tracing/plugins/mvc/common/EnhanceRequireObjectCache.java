package org.tracing.plugins.mvc.common;

import java.lang.reflect.Method;

public class EnhanceRequireObjectCache {
    private PathMappingCache pathMappingCache;

    public void setPathMappingCache(PathMappingCache pathMappingCache) {
        this.pathMappingCache = pathMappingCache;
    }

    public String findPathMapping(Method method) {
        return pathMappingCache.findPathMapping(method);
    }

    public void addPathMapping() {

    }

    public PathMappingCache getPathMappingCache() {
        return pathMappingCache;
    }
}
