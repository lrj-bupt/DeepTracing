package org.tracing.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RuntimeContext {
    private Map<Object, Object> context = new ConcurrentHashMap<>();
    public Object get(String key){
        return context.get(key);
    }

    public void put(Object key, Object value){
        this.context.put(key, value);
    }
}
