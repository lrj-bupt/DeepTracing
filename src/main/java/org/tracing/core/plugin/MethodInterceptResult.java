package org.tracing.core.plugin;

public class MethodInterceptResult {
    private boolean isContinue = true;
    private Object ret = null;

    public void defineReturnValue(Object ret) {
        this.isContinue = false;
        this.ret = ret;
    }

    public boolean isContinue() {
        return isContinue;
    }

    public Object _ret() {
        return ret;
    }
}
