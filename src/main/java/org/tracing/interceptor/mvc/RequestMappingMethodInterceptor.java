package org.tracing.interceptor.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class RequestMappingMethodInterceptor extends AbstractMethodInterceptor {

    public String getRequestURL(Method method) {
        return ParsePathUtil.recursiveParseMethodAnnotation(method, m->{
            String requestURL = null;
            RequestMapping methodRequestMapping = AnnotationUtils.getAnnotation(m, RequestMapping.class);
            if(methodRequestMapping != null) {
                requestURL = methodRequestMapping.value().length > 0? methodRequestMapping.value()[0] : "";
            }
            return requestURL;
        });
    }
}
