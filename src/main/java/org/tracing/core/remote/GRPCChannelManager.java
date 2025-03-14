package org.tracing.core.remote;

import org.tracing.core.boot.BootService;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GRPCChannelManager implements BootService, Runnable {
    private final Random random = new Random();
    private volatile List<String> grpcServers;
    private final List<GRPCChannelListener> listeners = Collections.synchronizedList(new LinkedList<>());
    private volatile int reconnectCount = 0;

    private volatile GRPCChannel managedChannel = null;

    @Override
    public void run(){
        int index = Math.abs(random.nextInt()) % grpcServers.size();
        String server = grpcServers.get(index);
        String[] split = server.split(":");
        managedChannel = GRPCChannel.newBuilder(split[0],Integer.parseInt(split[1])).build();
        notify(0);
    }

    @Override
    public void prepare() {

    }

    @Override
    public void boot() {
        // 获取grpcServers地址
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new RunnableWithExceptionProtection(this,
                        Throwable::printStackTrace),0,30, TimeUnit.SECONDS);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void shutdown() {

    }

    private void notify(Integer status){
        for (GRPCChannelListener listener : listeners) {
            listener.statusChanged();
        }
    }
}
