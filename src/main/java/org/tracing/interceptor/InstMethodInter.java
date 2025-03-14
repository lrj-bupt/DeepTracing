package org.tracing.interceptor;

import org.tracing.interceptor.interace.MethodInterceptor;
import net.bytebuddy.implementation.bind.annotation.*;
import org.tracing.core.plugin.MethodInterceptResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstMethodInter {
    private MethodInterceptor interceptor;
    private String interceptorName;
    public InstMethodInter(String interceptorName){
        this.interceptorName = interceptorName;
        Class<?> interceptorClass = null;
        try {
            interceptorClass = Class.forName(this.interceptorName);
            interceptor = (MethodInterceptor) interceptorClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] args, @SuperCall Callable<?> callable, @Origin Method method){
        EnhanceInstance targetObject = (EnhanceInstance) obj;
        MethodInterceptResult result = new MethodInterceptResult();
        try {
            interceptor.beforeMethod(targetObject, method, args, method.getParameterTypes(), result);
        }catch (Throwable t){
            System.out.println("方法拦截器调用失败" + t);
        }
        Object ret = null;
        try {
            ret = callable.call();

        }catch (Throwable t){
            System.out.println("方法拦截器调用失败" + t);
        }finally {
            interceptor.afterMethod(targetObject, method, args, method.getParameterTypes(), result);
        }
        return ret;
    }
}
