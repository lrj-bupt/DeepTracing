package org.tracing.core.logging.core;

import org.tracing.config.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class FileWriter implements IWriter{
    private static final Object LOCK = new Object();
    private static FileWriter INSTANCE;
    private FileOutputStream fileOutputStream;
    private ArrayBlockingQueue logBuffer;
    private volatile int fileSize;

    public static FileWriter get(){
        if(INSTANCE == null) {
            synchronized (LOCK){
                if(INSTANCE == null) {
                    INSTANCE = new FileWriter();
                }
            }
        }

        return INSTANCE;
    }

    private FileWriter() {
        logBuffer = new ArrayBlockingQueue(1024);
        final ArrayList<String> outputLogs = new ArrayList<String>(200);

        Thread logFlusherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        logBuffer.drainTo(outputLogs);
                        for (String outputLog : outputLogs) {

                        }
                        try {
                            if(fileOutputStream != null) {
                                fileOutputStream.flush();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }finally {
                        outputLogs.clear();
                    }

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){}
                }
            }
        });

        logFlusherThread.start();
    }

    private void writeToFile(String message) {
        try {
            fileOutputStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void switchFile() {
        if(fileSize > 10) {
            forceExecute(() -> {
                fileOutputStream.flush();
                return null;
            });

            forceExecute(() -> {
                fileOutputStream.close();
                return null;
            });
            forceExecute(() -> {
                new File("").renameTo(new File(""));
                return null;
            });
        }
    }

    private String[] getHistoryFilePath() {
        File path = new File(Config.Logging.DIR);
        String[] list = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return false;
            }
        });

        return list;
    }

    private void deleteExpiredFile() {
        String[] files = getHistoryFilePath();
        for (String file : files) {
            File f = new File(file);
            if(f.lastModified() < System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7) {
                f.delete();
            }
        }
    }

    private void forceExecute(Callable callable) {
        try {
            callable.call();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void write(String message) {
        logBuffer.offer(message);
    }
}
