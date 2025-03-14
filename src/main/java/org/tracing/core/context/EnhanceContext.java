package org.tracing.core.context;

public class EnhanceContext {
    private boolean isEnhanced = false;
    private boolean objectExtended = false;
    public boolean isEnhanced() {
        return isEnhanced;
    }

    public void initializationStageCompleted() {
        isEnhanced = true;
    }

    public boolean isObjectExtended() {
        return objectExtended;
    }

    public void extendObjectCompleted() {
        objectExtended = true;
    }
}
