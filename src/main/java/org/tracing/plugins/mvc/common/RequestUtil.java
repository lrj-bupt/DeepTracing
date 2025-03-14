package org.tracing.plugins.mvc.common;

import org.tracing.core.context.span.AbstractSpan;
import org.tracing.core.utils.CollectionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RequestUtil {
    public static void collectHttpParam(HttpServletRequest request, AbstractSpan span) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if(parameterMap != null && !parameterMap.isEmpty()) {
            String tagValue = CollectionUtil.toString(parameterMap);

        }
    }
}
