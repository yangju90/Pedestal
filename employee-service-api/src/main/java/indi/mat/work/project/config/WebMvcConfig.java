package indi.mat.work.project.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static indi.mat.work.project.util.Constant.SID;
import static indi.mat.work.project.util.Constant.TID;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 将CallChain的拦截器添加到所有/api开头的请求上
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CallChainInterceptor()).addPathPatterns("/**");
    }
    /**
     * 用于程序接收API Gateway传过来的Call Chain的两个Header，存放在RequestContext中
     */
    private static class CallChainInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

            String tid = request.getHeader(TID);
            if(StringUtils.hasText(tid)) {
                RequestContextHolder.currentRequestAttributes().setAttribute(TID, tid, RequestAttributes.SCOPE_REQUEST);
                MDC.putCloseable(TID,tid);
            }else{
                String uuid = UUID.randomUUID().toString();
                RequestContextHolder.currentRequestAttributes().setAttribute(TID, uuid, RequestAttributes.SCOPE_REQUEST);
                MDC.putCloseable(TID,uuid);
            }
            String sid = request.getHeader(SID);
            if(StringUtils.hasText(sid)) {
                RequestContextHolder.currentRequestAttributes().setAttribute(SID, sid, RequestAttributes.SCOPE_REQUEST);
                MDC.putCloseable(SID,sid);
            }


            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            MDC.clear();
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        }
    }

    /**
     * url 忽略大小写
     * @param config
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer config) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        pathMatcher.setCaseSensitive(false);
        config.setPathMatcher(pathMatcher);
    }
}
