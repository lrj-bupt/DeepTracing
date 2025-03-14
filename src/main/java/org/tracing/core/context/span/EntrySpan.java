package org.tracing.core.context.span;

import org.tracing.core.context.AbstractTracerContext;

import java.util.List;

public class EntrySpan extends StackBasedTracingSpan{
    private Integer currentMaxDepth;

    protected List<Object> logs;

    public void setCurrentMaxDepth(){

    }

    public EntrySpan(int spanId, int parentSpanId, String operationName, AbstractTracerContext owner){
        super(spanId, parentSpanId, operationName, owner);
    }

    public AbstractSpan start(){
        super.start();
        return this;
    }

    @Override
    public AbstractSpan setOperationName(String operationName) {
        super.setOperationName(operationName);
        return this;
    }

//    public EntrySpan tag(AbstractSpan tag, String value){
//        return null;
//    }

    public boolean isEntry() {
        return true;
    }

    public boolean isExit() {
        return false;
    }

}
