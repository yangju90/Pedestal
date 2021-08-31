@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware, ApplicationListener<WebServerInitializedEvent> {

    private static final String[] LOGIN_PATH_PATTERNS = { "/api/**" };
    private static final String[] LOGIN_EXCLUDE_PATH_PATTERNS = { "/faq",
            "/api/company/invitation/**",
            "/swagger**/**",
            "/webjars/**",
            "/v3/**","/doc.html",
            "/api/company/info/contract/approve",
            "/api/company/info/accounting/approve"};
    private static final String[] PERMISSION_PATH_PATTERNS = { "/api/**" };
    private static final String[] PERMISSION_EXCLUDE_PATH_PATTERNS = {
            "/faq",
            "/api/company/invitation/**",
            "/api/system/get",
            "/api/system/current/user",
            "/swagger**/**",
            "/webjars/**",
            "/v3/**",
            "/doc.html",
            "/api/system/dictionary/**",
            "/api/file/**",
            "/api/country/**",
            "/api/company/info/contract/approve",
            "/api/company/info/accounting/approve"};

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
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(LOGIN_PATH_PATTERNS)
                .excludePathPatterns(LOGIN_EXCLUDE_PATH_PATTERNS);
        //添加权限过滤器
        registry.addInterceptor(new PermissionInterceptor(permissionService))
                .addPathPatterns(PERMISSION_PATH_PATTERNS)
                .excludePathPatterns(PERMISSION_EXCLUDE_PATH_PATTERNS);
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
                    .setAttribute(LOGIN_USER_IN_REQUEST, loginUser, RequestAttributes.SCOPE_REQUEST);
            return true;
        }
    }
}
