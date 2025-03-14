package org.tracing.commons;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicRangeInteger {
    private int endValue;
    private int startValue;
    private AtomicIntegerArray values;
    public AtomicRangeInteger(int startValue, int maxValue){
        this.values = new AtomicIntegerArray(31);
        values.set(0, startValue);
        this.startValue = startValue;
        endValue = maxValue;
    }

    public final int getAndIncrement(){
        int next;
        do{
            next = this.values.incrementAndGet(0);
            if(next > endValue && this.values.compareAndSet(0, next, startValue)){
                return endValue;
            }
        }while (next > endValue);
        return next - 1;
    }
}
