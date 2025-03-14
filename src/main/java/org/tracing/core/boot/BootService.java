package org.tracing.core.boot;

public interface BootService {
    void prepare();

    void boot();

    void onComplete();

    void shutdown();
}
