package org.tracing.interceptor;

import org.tracing.interceptor.interace.InstanceConstructorInterceptor;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.InvocationTargetException;

public class InstConstruct {
    private InstanceConstructorInterceptor interceptor;
    public InstConstruct(String interceptorName){
        Class<?> classInst = null;
        try {
            classInst = Class.forName(interceptorName);
            interceptor = (InstanceConstructorInterceptor) classInst.getDeclaredConstructor().newInstance();
        }catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }
    @RuntimeType
    public void intercept(@This Object obj, @AllArguments Object[] args){
        try {
            EnhanceInstance targetObject = (EnhanceInstance) obj;
            interceptor.onConstruct(targetObject, args);
        }catch (Throwable t){
            System.out.println("构造函数拦截器调用失败" + t);
        }
    }
}
