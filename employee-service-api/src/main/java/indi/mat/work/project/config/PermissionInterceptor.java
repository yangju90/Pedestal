package indi.mat.work.project.config;

import indi.mat.work.project.exception.EmployeeException;
import indi.mat.work.project.model.user.LoginUser;
import indi.mat.work.project.service.account.IPermissionService;
import indi.mat.work.project.util.Constant;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        } catch (EmployeeException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
        if(hasUrlPermission){
            return true;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission Denied");
    }
}
