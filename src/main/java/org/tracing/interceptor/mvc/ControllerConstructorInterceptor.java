package org.tracing.interceptor.mvc;

import org.tracing.interceptor.interace.InstanceConstructorInterceptor;
import org.tracing.interceptor.EnhanceInstance;

public class ControllerConstructorInterceptor implements InstanceConstructorInterceptor {

    @Override
    public void onConstruct(EnhanceInstance obj, Object[] addArguments) {
        String basePath = "";

        obj.setSkyDynamicField(basePath);
    }
}
