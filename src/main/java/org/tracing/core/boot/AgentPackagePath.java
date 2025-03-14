package org.tracing.core.boot;

import org.tracing.core.logging.api.LogManager;
import org.tracing.core.logging.core.PatternLogger;
import org.tracing.core.plugin.loader.AgentClassLoader;

import java.io.File;
import java.net.URL;

public class AgentPackagePath {
    private static final PatternLogger LOGGER = LogManager.getLogger(AgentPackagePath.class);

    private static File AGENT_PACKAGE_PATH;

    public static File getPath() {
        if(AGENT_PACKAGE_PATH == null) {
            AGENT_PACKAGE_PATH = findPath();
        }
        return AGENT_PACKAGE_PATH;
    }

    public static File findPath(){
        String classResourcePath = AgentPackagePath.class.getName().replace("\\.","/") + ".class";
        System.out.println(classResourcePath);
//        URL resource = AgentClassLoader.class.getClassLoader().getResource(""); // 默认情况下定位到target/classes文件夹下 更精确的路径可以指定到文件夹
        URL resource = AgentClassLoader.class.getClassLoader().getResource(classResourcePath); // 默认情况下定位到target/classes文件夹下
        System.out.println(resource + "re");
        if(resource != null){
            String urlString = resource.toString();
            System.out.println(urlString);
            int indexOf = urlString.indexOf("!");
            boolean isInJar = indexOf > -1;

            if(isInJar){
                File agentJarFile = null;
                urlString = urlString.substring(urlString.indexOf("file:"), indexOf);
                try {
                    agentJarFile = new File(urlString);
                }catch (Exception e) {
                    LOGGER.error(e, "");
                }
                if(agentJarFile.exists()) {
                    return agentJarFile.getParentFile();
                }
            }else {
                int prefixLength = "file:".length();
                String classLocation = urlString.substring(prefixLength, urlString.length() - classResourcePath.length());
                return new File(classLocation);
            }
        }

        LOGGER.error("Can't find agent package path");
        throw new RuntimeException("");
    }
}
