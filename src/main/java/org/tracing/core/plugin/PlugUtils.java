package org.tracing.core.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PlugUtils {
    private List<Class<?>> plugins = new ArrayList<>();
    private List<URL> getResources(){
        List<URL> urls = new ArrayList<>();
        try {
            Enumeration<URL> resources = PlugUtils.class.getClassLoader().getResources("plugin.def");
            while (resources.hasMoreElements()){
                URL url = resources.nextElement();
                urls.add(url);
            }
            return urls;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Class<?>> loadPlugins(){
        List<URL> resources = getResources();
        for (URL url : resources) {
            try {
                load(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return plugins;
    }

    private void load(InputStream input){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String pluginDefine;
            while ((pluginDefine = bufferedReader.readLine()) != null) {
                String[] split = pluginDefine.split("=");
                String pluginName = split[0];
                String className = split[1];
                System.out.println("load plugin:" + pluginName + " class:" + className);
                Class<?> define = Class.forName(className);
                plugins.add(define);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
