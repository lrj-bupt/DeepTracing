package org.tracing.core.context;

public class ContextCarrier {
    private String traceId;

    private int spanId;

    private String traceSegmentId;

    private String parentService;

    private String parentServiceInstance;

    private String parentEndpoint;

    CorrelationContext correlationContext = new CorrelationContext();

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setTraceSegmentId(String traceSegmentId) {
        this.traceSegmentId = traceSegmentId;
    }

    public String getTraceSegmentId() {
        return traceSegmentId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setParentService(String parentService) {
        this.parentService = parentService;
    }

    public String getParentService() {
        return parentService;
    }

    public void setParentEndpoint(String parentEndpoint) {
        this.parentEndpoint = parentEndpoint;
    }

    public String getParentEndpoint() {
        return parentEndpoint;
    }

    public void setParentServiceInstance(String parentServiceInstance) {
        this.parentServiceInstance = parentServiceInstance;
    }

    public String getParentServiceInstance() {
        return parentServiceInstance;
    }

    void extractCorrelationTo(TracingContext tracingContext){

    }

    public CarrierItem items(){
        CorrelationCarrierItem correlationItem = new CorrelationCarrierItem(this.correlationContext, null);
        CarrierItem carrierItem = new CarrierItem(this, correlationItem);
        return new CarrierItemHead(carrierItem);
    }

    public String serialize(){
        return this.getTraceId() + "-" + this.getTraceSegmentId() + "-" + this.getParentService() + "-" + this.getSpanId() + "-" + this.getParentEndpoint();
    }
}
