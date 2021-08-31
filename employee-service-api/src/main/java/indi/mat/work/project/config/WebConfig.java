package indi.mat.work.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.mat.work.project.model.user.LoginUser;
import indi.mat.work.project.service.account.IPermissionService;
import indi.mat.work.project.util.AppConfig;
import indi.mat.work.project.util.Constant;
import indi.mat.work.project.util.JwtUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static indi.mat.work.project.util.Constant.LOGIN_USER_IN_REQUEST;
import static indi.mat.work.project.util.Constant.JWT_HEADER;


@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware, ApplicationListener<WebServerInitializedEvent> {

    private static final String[] LOGIN_PATH_PATTERNS = { "/api/**" };
    private static final String[] LOGIN_EXCLUDE_PATH_PATTERNS = {
            "/swagger**/**",
            "/v3/**","/doc.html"};
    private static final String[] PERMISSION_PATH_PATTERNS = { "/api/**" };
    private static final String[] PERMISSION_EXCLUDE_PATH_PATTERNS = {
            "/faq",
            "/api/system/get",
            "/api/system/current/user",
            "/swagger**/**",
            "/v3/**",
            "/doc.html",};

    private int port;
    private ApplicationContext applicationContext;

    @Autowired
    private AppConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPermissionService permissionService;

    public int getPort() {
        return port;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录过滤器
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns(LOGIN_PATH_PATTERNS)
//                .excludePathPatterns(LOGIN_EXCLUDE_PATH_PATTERNS);
        //添加权限过滤器
//        registry.addInterceptor(new PermissionInterceptor(permissionService))
//                .addPathPatterns(PERMISSION_PATH_PATTERNS)
//                .excludePathPatterns(PERMISSION_EXCLUDE_PATH_PATTERNS);
    }

    /**
     * 从jwt token中获取登录用户信息的拦截器
     */
    private class LoginInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String jwtToken = request.getHeader(JWT_HEADER);
            LoginUser loginUser = JwtUtil.verify(jwtToken, config.getJwtSecret());
            if(loginUser == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
            RequestContextHolder.currentRequestAttributes()
                    .setAttribute(Constant.LOGIN_USER_IN_REQUEST, loginUser, RequestAttributes.SCOPE_REQUEST);
            return true;
        }
    }
}
