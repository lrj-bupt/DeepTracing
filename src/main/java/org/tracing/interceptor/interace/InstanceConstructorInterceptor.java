package org.tracing.interceptor.interace;

import org.tracing.interceptor.EnhanceInstance;

public interface InstanceConstructorInterceptor {
    public void onConstruct(EnhanceInstance obj, Object[] addArguments);
}
