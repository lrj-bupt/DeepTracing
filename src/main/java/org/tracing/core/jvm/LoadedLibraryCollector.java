package org.tracing.core.jvm;

import org.tracing.core.logging.api.LogManager;
import org.tracing.core.logging.core.PatternLogger;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoadedLibraryCollector {
    private static Set<ClassLoader> CURRENT_URL_CLASSLOADER_SET = new HashSet<>();
    private static final PatternLogger LOGGER = LogManager.getLogger(LoadedLibraryCollector.class);

    private static String getVmStartTime() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime));
    }

    private static List<String> getVmArgs() {
        List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        ArrayList<String> strings = new ArrayList<>(arguments);
        Collections.sort(strings);
        return strings;
    }

    private static List<URL> loadClassLoaderUrls() {
        List<URL> classLoaderUrls = new ArrayList<>();
        for (ClassLoader classLoader : CURRENT_URL_CLASSLOADER_SET) {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            URL[] urLs = urlClassLoader.getURLs();
        }

        return classLoaderUrls;
    }
}
