package org.tracing.core.logging.core;

import org.tracing.config.Config;

public class WriterFactory {
    private static IWriter WRITER;
    public static IWriter getLogWriter() {
        switch (Config.Logging.OUTPUT){
            case FILE:
                WRITER = FileWriter.get();
        }

        return WRITER;
    }
}
