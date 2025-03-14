package org.tracing.core.plugin.loader;
//import jdk.internal.reflect.Reflection;
import org.tracing.core.boot.AgentPackagePath;
import org.tracing.core.logging.api.LogManager;
import org.tracing.core.logging.core.PatternLogger;
import org.tracing.core.plugin.PluginFinder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarFile;

public class AgentClassLoader extends ClassLoader{
    private static AgentClassLoader DEFAULT_LOADER;
    private List<File> classpath;

    static {
        registerAsParallelCapable();  // 允许并行加载
    }

    private static final PatternLogger LOGGER = LogManager.getLogger(AgentClassLoader.class);

    public static void initDefaultLoader(){
        if(DEFAULT_LOADER == null){
            synchronized (AgentClassLoader.class){
                if(DEFAULT_LOADER == null){
                    DEFAULT_LOADER = new AgentClassLoader(PluginFinder.class.getClassLoader());
                }
            }
        }
    }

    public AgentClassLoader(ClassLoader parent){
        super(parent);
        File agentDictionary = AgentPackagePath.getPath();
        classpath = new ArrayList<>();
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File jarFile = new File("");
            byte[] data;
            URL url = new URL("jar:file" + jarFile.getAbsolutePath() + "!");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            int ch;
            while((ch = bufferedInputStream.read()) != -1){
                arrayOutputStream.write(ch);
            }
            data = arrayOutputStream.toByteArray();
            Class<?> defineClass = defineClass(name, data, 0, data.length);
            return defineClass;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException("Can't find " + name);
    }

    private LinkedList<Jar> doGetJars() {
        LinkedList<Jar> jars = new LinkedList<>();
        for (File path : classpath) {
            if (path.exists() && path.isDirectory()) {
                String[] jarFileNames = path.list((dir, name) -> name.endsWith(".jar"));
                for (String fileName : jarFileNames) {
                    try {
                        File file = new File(path, fileName);
                        Jar jar = new Jar(new JarFile(file), file);
                        jars.add(jar);
                        LOGGER.info("{} loaded.", file.toString());
                    } catch (IOException e) {
                        LOGGER.error(e, "{} jar file can't be resolved", fileName);
                    }
                }
            }
        }
        return jars;
    }

    private static class Jar {
        private final JarFile jarFile;
        private final File sourceFile;
        public Jar(JarFile jarFile, File sourceFile) throws IOException {
            this.sourceFile = sourceFile;
            this.jarFile = new JarFile(sourceFile);
        }
    }
}
