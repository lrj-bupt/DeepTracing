package org.tracing.core.jvm.thread;

import org.tracing.network.language.metric.Thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public enum ThreadProvider {
    INSTANCE;
    private final ThreadMXBean threadMXBean;

    ThreadProvider() {
        threadMXBean = ManagementFactory.getThreadMXBean();
    }

    public Thread getThreadMetrics() {
        int runnableStateThreadCount = 0;
        int blockedStateThreadCount = 0;
        int waitingStateThreadCount = 0;
        int timedWaitingStateThreadCount = 0;

        // 为0表示不获取堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 0);
        if(threadInfos != null) {
            for (ThreadInfo threadInfo : threadInfos) {
                if(threadInfo == null) {
                    continue;
                }
                switch (threadInfo.getThreadState()) {
                    case RUNNABLE:
                        runnableStateThreadCount++;
                        break;
                    case BLOCKED:
                        blockedStateThreadCount++;
                        break;
                    case TIMED_WAITING:  // sleep(1000) 有时间限制
                        timedWaitingStateThreadCount++;
                        break;
                    case WAITING:
                        waitingStateThreadCount++;
                        break;
                    default:
                        break;
                }
            }
        }

        int threadCount = threadMXBean.getThreadCount();
        int daemonThreadCount = threadMXBean.getDaemonThreadCount();
        int peakThreadCount = threadMXBean.getPeakThreadCount();  // 获取数量峰值

        return Thread.newBuilder().setLiveCount(threadCount)
                .setDaemonCount(daemonThreadCount)
                .setRunnableStateThreadCount(runnableStateThreadCount)
                .setBlockedStateThreadCount(blockedStateThreadCount)
                .setWaitingStateThreadCount(waitingStateThreadCount)
                .setTimedWaitingStateThreadCount(timedWaitingStateThreadCount)
                .build();
    }
}
