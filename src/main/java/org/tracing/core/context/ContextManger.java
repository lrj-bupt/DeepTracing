package org.tracing.core.context;

import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.logging.api.LogManager;
import org.tracing.core.logging.core.PatternLogger;

import java.util.Objects;

public class ContextManger {
    private static final PatternLogger LOGGER = LogManager.getLogger(ContextManger.class);

    private static ThreadLocal<AbstractTracerContext> CONTEXT = new ThreadLocal<>();
    private static ThreadLocal<RuntimeContext> RUNTIME_CONTEXT = new ThreadLocal<>();

    public static RuntimeContext getRuntimeContext(){
        return RUNTIME_CONTEXT.get();
    }

    private static AbstractTracerContext getOrCreate(String operationName){
        AbstractTracerContext context = get();
        if(context == null){
            context = new TracingContext(operationName);
            CONTEXT.set(context);  // 别忘了加入线程本地变量
        }
        return context;
    }

    private static AbstractTracerContext get() {
        return CONTEXT.get();
    }

    public static String getGlobalTraceId(){
        AbstractTracerContext context = CONTEXT.get();
        return context.getTraceId();
    }

    public static int getSpanId(){
        AbstractTracerContext context = CONTEXT.get();
        return Objects.nonNull(context) ? context.getSpanId() : -1;
    }

    public static AbstractSpan createExitSpan(String operationName, ContextCarrier carrier, String remotePeer){
        AbstractTracerContext context;
        context = getOrCreate(operationName);
        // span中存储基本信息
        AbstractSpan exitSpan = context.createExitSpan(operationName, remotePeer);
        context.inject(carrier);
        return exitSpan;
    }

    public static AbstractSpan createEntrySpan(String operationName, ContextCarrier carrier){
        AbstractTracerContext context;
        context = getOrCreate(operationName);  // 消费端创建或是复用context
        if(carrier != null){
            context.extract(carrier);
        }
        AbstractSpan entrySpan = context.createEntrySpan(operationName);
        context.extract(carrier);
        return entrySpan;
    }

    public static void stopSpan(){
        AbstractTracerContext tracerContext = get();
        stopSpan(tracerContext.getActiveSpan(), tracerContext);
    }

    public static void inject(ContextCarrier carrier){
        get().inject(carrier);
    }

    public static void extract(ContextCarrier carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("ContextCarrier can't be null.");
        }
        get().extract(carrier);
    }

    private static void stopSpan(AbstractSpan span, AbstractTracerContext context){
        if (context.stopSpan(span)) {
            CONTEXT.remove();
            RUNTIME_CONTEXT.remove();
        }
    }

    public static AbstractSpan activeSpan() {
        return get().getActiveSpan();
    }

    public static void createExitSpan(String operationName, String remotePeer) {
    }
}
