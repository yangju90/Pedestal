package indi.mat.work.mq.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static indi.mat.work.mq.core.MqGatewayRequestHeaderConstants.SPANID;
import static indi.mat.work.mq.core.MqGatewayRequestHeaderConstants.TRACEID;

/**
 *
 */
public class MqGatewayRequestHeaderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<String, String> headerMap = new HashMap<>();
        String traceId = request.getHeader(TRACEID);
        String spanId = request.getHeader(SPANID);
        if(StringUtils.isNotBlank(traceId)){
            headerMap.put(TRACEID,traceId);
            headerMap.put(SPANID,spanId);
            MDC.putCloseable(TRACEID,traceId);
            MDC.putCloseable(SPANID,spanId);
        }else{
            headerMap.put(TRACEID,"");
            headerMap.put(SPANID,"");
        }
        MqGatewayRequestHeaderContextHolder.addRequestHeaders(headerMap);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.clear();
        /**出错也执行,用例清理数据之类的操作**/
        MqGatewayRequestHeaderContextHolder.removeRequestHeaders();
    }
}
