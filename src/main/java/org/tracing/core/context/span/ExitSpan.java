package org.tracing.core.context.span;

import org.tracing.core.context.AbstractTracerContext;

public class ExitSpan extends StackBasedTracingSpan{
    private String remotePeer;
    public ExitSpan(int spanId, int parentId, String operationName, String remotePeer, AbstractTracerContext owner){
        super(spanId, parentId, operationName, owner);
        this.remotePeer = remotePeer;
    }

    public String getRemotePeer(){
        return remotePeer;
    }

    public AbstractSpan setOperationName(String operationName) {
        super.setOperationName(operationName);
        return this;
    }

    public AbstractSpan start(){
        super.start();
        return this;
    }

    @Override
    public boolean isEntry() {
        return false;
    }

    @Override
    boolean isExit() {
        return false;
    }
}
