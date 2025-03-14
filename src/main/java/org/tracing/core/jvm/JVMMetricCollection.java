package org.tracing.core.jvm;

import org.tracing.network.language.metric.JVMMetric;

import java.util.LinkedList;
import java.util.List;

public class JVMMetricCollection {
    private String serviceName;
    private LinkedList<JVMMetric> metrics;

    public JVMMetricCollection() {
        metrics = new LinkedList<>();
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void addMetrics(List<JVMMetric> allMetrics) {
        metrics.addAll(allMetrics);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String serviceName;
        private LinkedList<JVMMetric> metrics;

        public Builder() {
            metrics = new LinkedList<>();
        }

        public Builder addMetrics(List<JVMMetric> allMetrics) {
            this.metrics.addAll(allMetrics);
            return this;
        }

        public Builder setServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public JVMMetricCollection build() {
            JVMMetricCollection jvmMetricCollection = new JVMMetricCollection();
            jvmMetricCollection.setServiceName(this.serviceName);
            jvmMetricCollection.addMetrics(this.metrics);
            return jvmMetricCollection;
        }
    }
}
