public class PermissionInterceptor implements HandlerInterceptor {

    private final IPermissionService permissionService;

    public PermissionInterceptor(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取当前登录用户
        LoginUser user = (LoginUser)request.getAttribute(Constant.LOGIN_USER_IN_REQUEST);
        //获取请求方法
        String method = request.getMethod();
        //获取请求url
        String url = request.getRequestURI();
        boolean hasUrlPermission;
        try {
            hasUrlPermission = permissionService.hasPermission(url, HttpMethod.resolve(method), user);
        } catch (NeweggStaffingException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
        if(hasUrlPermission){
            return true;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission Denied");
    }
}
