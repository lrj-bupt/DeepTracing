package org.tracing.core.context;

import org.tracing.config.Config;
import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.context.span.EntrySpan;
import org.tracing.core.context.span.ExitSpan;
import org.tracing.core.context.span.StackBasedTracingSpan;
import org.tracing.core.context.ids.DistributeTraceId;

import java.util.LinkedList;
import java.util.List;

public class TracingContext implements AbstractTracerContext{
    private CorrelationContext correlationContext;
    private TraceSegment segment;
    private int spanIdGenerator;
    private LinkedList<AbstractSpan> activeSpan = new LinkedList<>();
    public TracingContext(String firstOPName){
        segment = new TraceSegment();
        correlationContext = new CorrelationContext();
    }
    public void inject(ContextCarrier carrier){
        AbstractSpan activeSpan = getActiveSpan();
        inject(activeSpan, carrier);
    }

    public void inject(AbstractSpan span, ContextCarrier carrier){
        // 注入全局id
        carrier.setTraceId(getTraceId());
        carrier.setTraceSegmentId(segment.getTraceSegmentId());
        carrier.setSpanId(span.getSpanId());
        carrier.setParentService(Config.SERVICE_NAME);
        carrier.setParentEndpoint(Config.INSTANCE_NAME);
        carrier.setParentServiceInstance(span.getOperationName());  // operation name
        correlationContext.inject(carrier);
    }

    public void extract(ContextCarrier carrier){
        TraceSegmentRef ref = new TraceSegmentRef(carrier);
        this.segment.ref(ref);
        this.segment.setRelatedGlobalTraceId(new DistributeTraceId(carrier.getTraceId()));
        // todo
        carrier.extractCorrelationTo(this);
    }

    public String getTraceId(){
        return segment.getRelatedGlobalTraceId().getId();
    }

    public int getSpanId(){
        return getActiveSpan().getSpanId();
    }

    public AbstractSpan createEntrySpan(String operationName){
        AbstractSpan entrySpan;
        AbstractSpan parentSpan = peek();
        // 第一个span id为-1
        final int parentSpanId = parentSpan == null ? -1 : parentSpan.getSpanId();

        if(parentSpan != null && parentSpan.isEntry()) {
            parentSpan.setOperationName(operationName);
            entrySpan = parentSpan;  // 复用
            return entrySpan.start();
        }else {
            entrySpan = new EntrySpan(spanIdGenerator++, parentSpanId, operationName, this);
        }
        entrySpan.start();
        return push(entrySpan);
    }

    public AbstractSpan createExitSpan(String operationName, String remotePeer){
        AbstractSpan exitSpan;
        AbstractSpan parentSpan = peek();
        int parentSpanId;
        if(parentSpan == null){
            parentSpanId = -1;
            exitSpan = new ExitSpan(spanIdGenerator++, parentSpanId, operationName, remotePeer, this);
        }else {
            parentSpanId = parentSpan.getSpanId();
            exitSpan = parentSpan;
        }
        exitSpan.start();
        return push(exitSpan);
    }

    public AbstractSpan getActiveSpan(){
        AbstractSpan peek = peek();
        if(peek == null){
            System.out.println("active span is null");
        }
        return peek;
    }

    @Override
    public boolean stopSpan(AbstractSpan span) {
        AbstractSpan lastSpan = peek();
        if(lastSpan == span){
            if(lastSpan instanceof StackBasedTracingSpan){
                lastSpan.finish(segment);
                pop();
            }
        }
        finish();

        return activeSpan.isEmpty();
    }

    private void finish(){
        // 通知监听器结束事件 如果队列已经为空
        boolean isEmpty = activeSpan.isEmpty();
        if(isEmpty){
            ListenerManager.notifyFinish(segment);
        }
    }

    public AbstractSpan peek(){
        if(activeSpan.isEmpty()){
            return null;
        }
        return activeSpan.getLast();
    }

    public AbstractSpan push(AbstractSpan span){
        activeSpan.addLast(span);
        return span;
    }

    public AbstractSpan pop(){
        return activeSpan.removeLast();
    }

    public static class ListenerManager{
        private static List<TracingContextListener> LISTENERS = new LinkedList<>();
        public static void add(TracingContextListener listener){
            LISTENERS.add(listener);
        }

        static void notifyFinish(TraceSegment segment){
            for (TracingContextListener listener : LISTENERS) {
                listener.afterFinished(segment);
            }
        }
    }

    public static class TracingThreadListenerManager{
        private static List<TracingThreadListener> LISTENERS = new LinkedList<>();
        public static void add(TracingThreadListener listener){
            LISTENERS.add(listener);
        }

        public static void remove(TracingThreadListener listener){
            LISTENERS.remove(listener);
        }

        public static void notifyFinish(TracingContext tracingContext){
            for (TracingThreadListener listener : LISTENERS) {
                listener.afterMainThreadFinish(tracingContext);
            }
        }
    }
}
