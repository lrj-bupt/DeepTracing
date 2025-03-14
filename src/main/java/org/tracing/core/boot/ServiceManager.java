package org.tracing.core.boot;

import org.tracing.core.logging.api.LogManager;
import org.tracing.core.logging.core.PatternLogger;

import java.util.*;

public enum ServiceManager {
    INSTANCE;
    private static final PatternLogger LOGGER = LogManager.getLogger(ServiceManager.class);
    private Map<Class, BootService> bootServices = Collections.emptyMap();

    public void boot(){
        bootServices = loadAllServices();

        prepare();
        startup();
        onComplete();
    }

    public void prepare(){
        bootServices.values().forEach(service->{
            try {
                service.prepare();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void shutdown(){
        bootServices.values().forEach(service->{
            try {
                service.shutdown();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void startup(){
        bootServices.values().forEach(service->{
            try {
                service.boot();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public Map<Class, BootService> loadAllServices(){
        Map<Class, BootService> bootServiceMap = new LinkedHashMap<>();
        List<BootService> allServices = new LinkedList<>();
        load(allServices);
        for (BootService service : allServices) {
            bootServiceMap.put(service.getClass(), service);
        }
        return bootServiceMap;
    }

    public void onComplete(){
        bootServices.values().forEach(service->{
            try {
                service.onComplete();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void load(List<BootService> allServices){
        ServiceLoader<BootService> services = ServiceLoader.load(BootService.class);
        for (BootService service : services) {
            allServices.add(service);
        }

    }

    public <T extends BootService> T findService(Class<T> serviceClass){
        return (T) bootServices.get(serviceClass);
    }
}
