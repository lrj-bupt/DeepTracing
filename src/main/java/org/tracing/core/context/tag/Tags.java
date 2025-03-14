package org.tracing.core.context.tag;

public class Tags {
    public static final StringTag URL = new StringTag("url");
    public static final IntegerTag HTTP_RESPONSE_STATUS_CODE = new IntegerTag("http.status_code");

    public static final StringTag DB_TYPE = new StringTag("db.type");
    public static final StringTag DB_INSTANCE = new StringTag( "db.instance");
    public static final StringTag DB_STATEMENT = new StringTag("db.statement");

    public static final StringTag MQ_QUEUE = new StringTag("mq.queue");
    public static final StringTag MQ_TOPIC = new StringTag("mq.topic");
    public static final StringTag MQ_BREAK = new StringTag("mq.break");

    public static final StringTag CACHE_TYPE = new StringTag("cache.type");
    public static final StringTag CACHE_KEY = new StringTag("cache.key");
    public static final StringTag CACHE_CMD = new StringTag("cache.cmd");

    public static final class HTTP{
        public static final StringTag METHOD = new StringTag("http.method");
        public static final StringTag PARAMS = new StringTag("http.params");
        public static final StringTag BODY = new StringTag("http.body");
        public static final StringTag HEADERS = new StringTag("http.headers");
    }

}
