package org.tracing.config;

import org.tracing.core.logging.core.LogOutput;

public class Config {
    // 一个微服务下可能有多个实例
    public static String SERVICE_NAME = "";
    public static String INSTANCE_NAME = "";

    public static class Jvm {
        public static int BUFFER_SIZE = 60 * 10;
        public static int METRIC_COLLECT_INTERVAL = 60;
    }

    public static class Buffer {
        public static int BUFFER_SIZE = 300;
        public static int CHANNEL_SIZE = 5;
    }

    public static class Logging {
        public static LogOutput OUTPUT = LogOutput.FILE;
        public static String FILE_NAME = "tracing.log";
        public static String DIR = "";
        public static int MAX_FILE_SIZE = 1024 * 1024 * 100;
        public static String PATTERN = "%level %timestamp %thread %class : %msg %throwable";
    }
}
