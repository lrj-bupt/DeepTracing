package org.tracing.core.boot;

import org.tracing.core.context.TracingContext;
import org.tracing.core.context.TracingThreadListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProfileTaskService implements BootService, TracingThreadListener {
    private final ScheduledExecutorService PROFILE_TASK_SCHEDULE = Executors.newSingleThreadScheduledExecutor(

    );
    @Override
    public void prepare() {

    }

    @Override
    public void boot() {

    }

    @Override
    public void onComplete() {
        TracingContext.TracingThreadListenerManager.add(this);
    }

    @Override
    public void shutdown() {
        TracingContext.TracingThreadListenerManager.remove(this);
        PROFILE_TASK_SCHEDULE.shutdown();
    }

    @Override
    public void afterMainThreadFinish(TracingContext tracingContext) {

    }
}
