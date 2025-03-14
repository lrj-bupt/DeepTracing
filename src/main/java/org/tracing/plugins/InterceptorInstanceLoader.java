package org.tracing.plugins;

import org.tracing.core.plugin.loader.AgentClassLoader;

import java.util.HashMap;
import java.util.Map;

public class InterceptorInstanceLoader {
    private static Map<ClassLoader, ClassLoader> EXTEND_PLUGIN_CLASSLOADER = new HashMap<>();


    public static <T> T load(String className, ClassLoader targetClassLoader) throws ClassNotFoundException {
        Object inst = null;
        ClassLoader classLoader = EXTEND_PLUGIN_CLASSLOADER.get(targetClassLoader);
        if(classLoader == null) {
            classLoader = new AgentClassLoader(targetClassLoader);
            EXTEND_PLUGIN_CLASSLOADER.put(targetClassLoader, classLoader);
        }
        inst = Class.forName(className, true, classLoader);
        return (T)inst;
    }
}
