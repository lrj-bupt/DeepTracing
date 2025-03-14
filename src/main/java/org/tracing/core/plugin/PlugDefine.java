package org.tracing.core.plugin;

import org.tracing.core.context.EnhanceContext;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.point.interceptor.MethodInterceptorPoint;
import org.tracing.interceptor.EnhanceInstance;
import org.tracing.interceptor.InstConstruct;
import org.tracing.interceptor.InstMethodInter;
import org.tracing.interceptor.InstMethodWithOverrideArgs;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Morph;


import static net.bytebuddy.jar.asm.Opcodes.ACC_PRIVATE;
import static net.bytebuddy.jar.asm.Opcodes.ACC_VOLATILE;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;

public abstract class PlugDefine {
    public DynamicType.Builder<?> enhance(TypeDescription typeDescription, DynamicType.Builder<?> newClassBuilder, ClassLoader classLoader, EnhanceContext enhanceContext) {
        newClassBuilder = this.enhanceClass(typeDescription, newClassBuilder, classLoader);
        newClassBuilder = this.enhanceInstance(typeDescription, newClassBuilder, classLoader, enhanceContext);
        return newClassBuilder;
    }

    // 加强静态方法  满足指定条件并移交给代理类
    public DynamicType.Builder<?> enhanceClass(TypeDescription typeDescription, DynamicType.Builder<?> newClassBuilder, ClassLoader classLoader) {
        MethodInterceptorPoint[] staticMethodInterceptorPoint = this.getStaticMethodInterceptorPoint();
        if (staticMethodInterceptorPoint == null || staticMethodInterceptorPoint.length == 0) {
            return newClassBuilder;
        }
        for (MethodInterceptorPoint methodInterceptorPoint : staticMethodInterceptorPoint) {
            String interceptor = methodInterceptorPoint.getInterceptor();
            newClassBuilder = newClassBuilder.method(isStatic().and(methodInterceptorPoint.getMethodMatcher())).intercept(MethodDelegation.to(new InstMethodInter(interceptor)));
        }
        return newClassBuilder;
    }

    // 注入属性 修改构造器和方法
    public DynamicType.Builder<?> enhanceInstance(TypeDescription typeDescription, DynamicType.Builder<?> newClassBuilder, ClassLoader classLoader, EnhanceContext context) {

        MethodInterceptorPoint[] constructInterceptorPoint = this.getConstructInterceptorPoint();
        boolean existConstructorInterceptor = constructInterceptorPoint != null && constructInterceptorPoint.length > 0;
        if(!typeDescription.isAssignableTo(EnhanceInstance.class)){
            System.out.println(typeDescription.getTypeName() + " is not enhanced");
            if(!context.isObjectExtended()){
                newClassBuilder = newClassBuilder.defineField("_$EnhancedField", Object.class, ACC_PRIVATE | ACC_VOLATILE)
                        .implement(EnhanceInstance.class).intercept(FieldAccessor.ofField("_$EnhancedField"));
            }
            context.extendObjectCompleted();  // 非常重要
        }
        if(existConstructorInterceptor){
            for (MethodInterceptorPoint point : constructInterceptorPoint) {
                String interceptor = point.getInterceptor();
                newClassBuilder = newClassBuilder.constructor(point.getMethodMatcher()).intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(new InstConstruct(interceptor))));
            }
        }

        MethodInterceptorPoint[] interceptorPoints = this.getInstanceMethodInterceptorPoints();
        for (MethodInterceptorPoint point : interceptorPoints) {
            String interceptor = point.getInterceptor();
            if(point.isOverrideArgs()){
                newClassBuilder = newClassBuilder.method(point.getMethodMatcher()).intercept(MethodDelegation.withDefaultConfiguration().withBinders(Morph.Binder.install(OverrideCallable.class)).to(new InstMethodWithOverrideArgs(interceptor)));
            }else {
                newClassBuilder = newClassBuilder.method(point.getMethodMatcher()).intercept(MethodDelegation.withDefaultConfiguration().to(new InstMethodInter(interceptor)));
            }
        }
        return newClassBuilder;
    }

    public abstract ClassMatch enhanceClassName();

    public abstract MethodInterceptorPoint[] getInstanceMethodInterceptorPoints();

    public abstract MethodInterceptorPoint[] getStaticMethodInterceptorPoint();

    public abstract MethodInterceptorPoint[] getConstructInterceptorPoint();
}
