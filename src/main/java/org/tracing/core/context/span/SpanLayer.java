package org.tracing.core.context.span;

public enum SpanLayer {
    DB(1), RPC_FRAMEWORK(2), HTTP(3), MQ(4), CACHE(5);
    private final int code;
    SpanLayer(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static void asDB(AbstractSpan span) {
        span.setLayer(SpanLayer.DB);
    }

    public static void asRPCFramework(AbstractSpan span) {
        span.setLayer(SpanLayer.RPC_FRAMEWORK);
    }

    public static void asHttp(AbstractSpan span) {
        span.setLayer(SpanLayer.HTTP);
    }

    public static void asMQ(AbstractSpan span) {
        span.setLayer(SpanLayer.MQ);
    }

    public static void asCache(AbstractSpan span) {
        span.setLayer(SpanLayer.CACHE);
    }
}
