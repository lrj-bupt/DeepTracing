package org.tracing.core.plugin;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.tracing.core.plugin.match.ClassMatch;
import org.tracing.core.plugin.match.NameMatch;
import org.tracing.core.plugin.match.SignatureMatch;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class PluginFinder {
    public static HashMap<String, LinkedList<PlugDefine>> pluginMap = new HashMap<>();
    public static LinkedList<PlugDefine> signatureMap = new LinkedList<>();

    public PluginFinder(List<Class<?>> pluginClassList) {
        for (Class<?> pluginClass : pluginClassList) {
            try {
                PlugDefine define = (PlugDefine) pluginClass.getDeclaredConstructor().newInstance();
                ClassMatch classMatch = define.enhanceClassName();
                if (classMatch instanceof NameMatch) {
                    if (pluginMap.containsKey(((NameMatch) classMatch).getName())) {
                        pluginMap.get(((NameMatch) classMatch).getName()).add(define);
                    } else {
                        LinkedList<PlugDefine> list = new LinkedList<>();
                        list.add(define);
                        pluginMap.put(((NameMatch) classMatch).getName(), list);
                    }
                } else {
                    signatureMap.add(define);
                }

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PlugDefine> find(TypeDescription description) {
        String actualName = description.getActualName();
        LinkedList<PlugDefine> matchedList = new LinkedList<>();
        if (pluginMap.containsKey(actualName)) {
            matchedList.addAll(pluginMap.get(actualName));
        }
        for (PlugDefine plugDefine : signatureMap) {
            SignatureMatch match = (SignatureMatch) plugDefine.enhanceClassName();
            if (match.isMatch(description)) {
                matchedList.add(plugDefine);
            }
        }
        return matchedList;
    }

    public ElementMatcher<? super TypeDescription> buildMatch() {
        ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
            @Override
            public boolean matches(NamedElement namedElement) {
                return pluginMap.containsKey(namedElement.getActualName());
            }
        };

        judge = judge.and(not(isInterface()));
        for (PlugDefine plugDefine : signatureMap) {
            SignatureMatch classMatch = (SignatureMatch) plugDefine.enhanceClassName();
            judge = judge.or(classMatch.buildJunction());
        }
        return new ProtectiveShieldMatcher(judge);
    }
}
