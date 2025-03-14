package org.tracing.core.context.ids;

public class DistributeTraceId {
    private String id;
    public DistributeTraceId(String id){
        this.id = id;
    }

    public DistributeTraceId(){
        this.id = GlobalIdGenerator.generate();
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
}
