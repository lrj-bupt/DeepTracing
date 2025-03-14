package org.tracing.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CorrelationContext {
    Map<String, String> data;
    public CorrelationContext(){
        this.data = new ConcurrentHashMap<>();
    }
    // 将数据输送到其他context中
    public void inject(ContextCarrier carrier){
        carrier.correlationContext.data.putAll(this.data);
    }

    public void extract(ContextCarrier carrier){
        Map<String, String> data = carrier.correlationContext.data;
        this.data.putAll(data);
    }

    public String serialize(){
        if(data.isEmpty()){
            return "";
        }
        return  data.entrySet().stream()
                .map(entry-> entry.getKey() + ":" +  entry.getValue())
                .collect(Collectors.joining(","));
    }
}
