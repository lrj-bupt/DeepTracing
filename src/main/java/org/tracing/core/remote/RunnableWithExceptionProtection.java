package org.tracing.core.remote;

public class RunnableWithExceptionProtection implements Runnable{
    private Runnable runnable;
    private CallbackWhenException callback;
    public RunnableWithExceptionProtection(Runnable run, CallbackWhenException callback){
        this.runnable = run;
        this.callback = callback;
    }
    public void run(){
        try {
            runnable.run();
        }catch (Exception e){
            callback.handle(e);
        }
    }

    public interface CallbackWhenException{
        void handle(Throwable t);
    }
}
