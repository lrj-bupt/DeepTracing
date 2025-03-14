package org.tracing.core.jvm;

import org.tracing.core.jvm.clazz.ClassProvider;
import org.tracing.core.jvm.cpu.CpuProvider;
import org.tracing.core.boot.BootService;
import org.tracing.core.boot.ServiceManager;
import org.tracing.config.Config;
import org.tracing.core.jvm.gc.GCProvider;
import org.tracing.core.jvm.memorypool.MemoryPoolProvider;
import org.tracing.core.jvm.thread.ThreadProvider;
import org.tracing.network.language.metric.JVMMetric;
import org.apache.catalina.core.StandardHost;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class JVMServiceClient implements BootService, Runnable {
    private ScheduledFuture<?> collectMetricFuture;
    private ScheduledFuture<?> sendMetricFuture;

    private RabbitMqJvmSender sender;

    @Override
    public void prepare() {
        sender = ServiceManager.INSTANCE.findService(RabbitMqJvmSender.class);
    }

    @Override
    public void boot() {
        StandardHost host = new StandardHost();
        DispatcherServlet a;
        collectMetricFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 0, Config.Jvm.METRIC_COLLECT_INTERVAL, TimeUnit.SECONDS);

        sendMetricFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(sender, 0, 60, TimeUnit.SECONDS);
    }

    @Override
    public void onComplete() {
        // 可以划分为两类 一类是定时任务 另一类需要注册自身，等待回调
    }

    @Override
    public void shutdown() {

    }

    // 采集jvm数据
    @Override
    public void run() {
        JVMMetric.Builder builder = JVMMetric.newBuilder();
        builder.setTime(System.currentTimeMillis());
        builder.setCpu(CpuProvider.INSTANCE.getCpuMetric());

        builder.addAllMemory(MemoryProvider.INSTANCE.getMemoryMetricList());
        builder.addAllMemoryPool(MemoryPoolProvider.INSTANCE.getMemoryPoolMetricsList());
        builder.addAllGc(GCProvider.INSTANCE.getGCList());
        builder.setThread(ThreadProvider.INSTANCE.getThreadMetrics());
        builder.setClazz(ClassProvider.INSTANCE.getClassMetric());
        JVMMetric metric = builder.build();

        sender.offer(metric);
    }
}
