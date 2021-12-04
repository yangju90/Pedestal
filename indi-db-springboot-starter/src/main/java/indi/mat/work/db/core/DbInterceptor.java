package indi.mat.work.db.core;

import indi.mat.work.db.util.ThreadLocalUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DbInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getMethod());
        ThreadLocalUtils.setTraceId(request.getHeader("traceId"));
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(ThreadLocalUtils.getTraceId());
        System.out.println(ThreadLocalUtils.getMethodName());
        ThreadLocalUtils.clear();
    }
}
