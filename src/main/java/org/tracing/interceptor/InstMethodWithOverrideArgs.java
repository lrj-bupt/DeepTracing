package org.tracing.interceptor;

import org.tracing.core.plugin.MethodInterceptResult;
import org.tracing.core.plugin.OverrideCallable;
import org.tracing.interceptor.interace.MethodInterceptor;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InstMethodWithOverrideArgs {
    private MethodInterceptor interceptor;
    private String interceptorName;
    public InstMethodWithOverrideArgs(String interceptorName){
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
    public Object intercept(@This Object obj, @AllArguments Object[] args, @Origin Method method, @Morph OverrideCallable zuper){
        EnhanceInstance enhanceInstance = (EnhanceInstance) obj;
        MethodInterceptResult result = new MethodInterceptResult();

        try {
            interceptor.beforeMethod(enhanceInstance, method, args, method.getParameterTypes(), result);
        }catch (Throwable t){

        }
        Object ret = null;
        try {
            if(!result.isContinue()) {
                ret = result._ret();
            }else{
                ret = zuper.call(args);
            }

        }catch (Throwable t){

        }finally {
            interceptor.afterMethod(enhanceInstance, method, args, method.getParameterTypes(), ret);
        }
        return result;
    }
}
