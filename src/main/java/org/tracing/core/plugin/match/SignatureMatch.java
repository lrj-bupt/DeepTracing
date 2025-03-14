package org.tracing.core.plugin.match;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.annotation.Annotation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class SignatureMatch implements ClassMatch {
    private String annoName;
    public SignatureMatch(String annoName){
        this.annoName = annoName;
    }
    public boolean isMatch(TypeDescription description){
        try {
            return description.getDeclaredAnnotations().isAnnotationPresent((Class<? extends Annotation>) Class.forName(annoName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ElementMatcher.Junction buildJunction(){
        ElementMatcher.Junction junction = null;
        junction = buildAnnotation();
        junction.and(not(isInterface()));
        return junction;
    }

    private ElementMatcher.Junction buildAnnotation(){
        try {
            return isAnnotatedWith((Class<? extends Annotation>) Class.forName(annoName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
