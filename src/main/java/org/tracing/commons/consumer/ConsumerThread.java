package org.tracing.commons.consumer;

import org.tracing.commons.buffer.QueueBuffer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerThread<T> extends Thread {
    private IConsumer<T> consumer;
    private boolean running;
    private List<DataSource> dataSources;

    ConsumerThread(String threadName, IConsumer<T> consumer) {
        super(threadName);
        running = false;
        this.consumer = consumer;
        dataSources = new ArrayList<DataSource>(1);
    }

    public void run() {
        running = true;
        final List<T> consumeList = new ArrayList<T>(1500);

        while (running) {
            // 没有任务就短暂休眠 有任务就继续执行
            if (!consume(consumeList)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        consume(consumeList);  // 确保剩余数据能消费完
    }

    private boolean consume(List<T> consumeList) {
        for (DataSource dataSource : dataSources) {
            dataSource.obtain(consumeList);
        }

        if (!consumeList.isEmpty()) {
            consumer.consume(consumeList);
            return true;
        }
        return false;
    }

    public void shutdown() {
        running = false;
    }

    public void addDataSource(QueueBuffer buffer) {
        this.dataSources.add(new DataSource(buffer));
    }

    class DataSource {
        QueueBuffer<T> queueBuffer;  // 环形数组存储数据

        public DataSource(QueueBuffer<T> buffer) {
            this.queueBuffer = buffer;
        }

        // 将数据放入链表中
        void obtain(List<T> consumeList) {
            queueBuffer.obtain(consumeList);
        }
    }
}
