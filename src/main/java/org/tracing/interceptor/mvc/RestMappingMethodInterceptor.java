package org.tracing.interceptor.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

public class RestMappingMethodInterceptor extends AbstractMethodInterceptor {

    @Override
    public String getRequestURL(Method method) {
        return ParsePathUtil.recursiveParseMethodAnnotation(method, m->{
            String requestURL = null;
            GetMapping getMapping = AnnotationUtils.getAnnotation(m, GetMapping.class);
            PostMapping postMapping = AnnotationUtils.getAnnotation(m, PostMapping.class);
            PutMapping putMapping = AnnotationUtils.getAnnotation(m, PutMapping.class);
            DeleteMapping deleteMapping = AnnotationUtils.getAnnotation(m, DeleteMapping.class);
            PatchMapping patchMapping = AnnotationUtils.getAnnotation(m, PatchMapping.class);
            if (getMapping != null) {
                if (getMapping.value().length > 0) {
                    requestURL = getMapping.value()[0];
                } else if (getMapping.path().length > 0) {
                    requestURL = getMapping.path()[0];
                }
            }else if (postMapping != null){
                if (postMapping.value().length > 0) {
                    requestURL = postMapping.value()[0];
                } else if (postMapping.path().length > 0) {
                    requestURL = postMapping.path()[0];
                }
            } else if (putMapping != null){
                if (putMapping.value().length > 0) {
                    requestURL = putMapping.value()[0];
                } else if (putMapping.path().length > 0) {
                    requestURL = putMapping.path()[0];
                }
            } else if (deleteMapping != null){
                if (deleteMapping.value().length > 0) {
                    requestURL = deleteMapping.value()[0];
                } else if (deleteMapping.path().length > 0) {
                    requestURL = deleteMapping.path()[0];
                }
            } else if (patchMapping != null){
                if (patchMapping.value().length > 0) {
                    requestURL = patchMapping.value()[0];
                } else if (patchMapping.path().length > 0) {
                    requestURL = patchMapping.path()[0];
                }
            }

            return requestURL;
        });
    }
}
