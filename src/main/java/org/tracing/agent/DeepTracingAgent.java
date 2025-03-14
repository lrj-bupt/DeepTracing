package org.tracing.agent;

import org.tracing.core.boot.ServiceManager;
import org.tracing.core.context.EnhanceContext;
import org.tracing.core.plugin.PlugUtils;
import org.tracing.core.plugin.PluginFinder;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import org.tracing.core.plugin.PlugDefine;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class DeepTracingAgent {
    private static PluginFinder pluginFinder;
    public static void premain(String agentArgs, java.lang.instrument.Instrumentation inst) {
        pluginFinder = new PluginFinder(new PlugUtils().loadPlugins());

        ByteBuddy byteBuddy = new ByteBuddy();
        new AgentBuilder.Default(byteBuddy)
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("sun.reflect"))
                        .or(nameContains(".asm."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy.")))
                .type(pluginFinder.buildMatch())
                .transform(new Transformer())
                .installOn(inst);

        try {
            ServiceManager.INSTANCE.boot();
        }catch (Exception e){
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(ServiceManager.INSTANCE::shutdown,"service shutdown thread"));
    }

    public static void agentmain(String agentArgs, java.lang.instrument.Instrumentation inst) {
        ByteBuddy byteBuddy = new ByteBuddy();
        new AgentBuilder.Default(byteBuddy)
                .type(nameStartsWith("org/tracing/demo"))
                .transform(new Transformer())
                .with(new Listener())
                .installOn(inst);
    }

    public static class Transformer implements AgentBuilder.Transformer{
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
            List<PlugDefine> plugDefines = pluginFinder.find(typeDescription);
            EnhanceContext context = new EnhanceContext();
            System.out.println("enhance class " + typeDescription.getTypeName() + " with " + plugDefines.size() + " plugins.");
            for (PlugDefine plugdefine : plugDefines) {
                builder = plugdefine.enhance(typeDescription, builder, classLoader, context);
            }
            return builder;
        }
    }

    private static class Listener implements net.bytebuddy.agent.builder.AgentBuilder.Listener {

        @Override
        public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }

        @Override
        public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
            System.out.println("Enhance class " + s + " error." + throwable);
        }

        @Override
        public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }
    }

}
