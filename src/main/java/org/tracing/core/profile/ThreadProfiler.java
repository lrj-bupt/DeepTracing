package org.tracing.core.profile;

public class ThreadProfiler {
    private final Thread profilerThread;

    public ThreadProfiler(Thread thread){
        this.profilerThread = thread;
    }

    public void buildSnapshot(){
        StackTraceElement[] stackTrace = profilerThread.getStackTrace();

    }
}
