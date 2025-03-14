package org.tracing.core.plugin.point;

import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.annotation.Annotation;

public class AnnotationTypeMatch implements ElementMatcher<MethodDescription> {
    private final String annotationTypeName;
    public AnnotationTypeMatch(String annotationTypeName) {
        this.annotationTypeName = annotationTypeName;
    }
    @Override
    public boolean matches(MethodDescription methodDescription) {
        Class<?> annotationType;
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        AnnotationList annotations = methodDescription.getDeclaredAnnotations();
        if (annotations.isAnnotationPresent((Class<? extends Annotation>) annotationType)) {
            return true;
        }
        return false;
    }

    public static ElementMatcher<MethodDescription> isAnnotationPresent(String annotationTypeName) {
        return new AnnotationTypeMatch(annotationTypeName);
    }
}
