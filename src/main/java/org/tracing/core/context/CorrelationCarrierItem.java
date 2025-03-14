package org.tracing.core.context;

public class CorrelationCarrierItem extends CarrierItem{
    public CorrelationCarrierItem(String headKey, String headValue) {
        super(headKey, headValue);
    }

    public CorrelationCarrierItem(CorrelationContext correlationContext, CarrierItem next) {
        super("correlation", correlationContext.serialize(), next);
    }
}
