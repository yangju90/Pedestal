package indi.mat.work.project.service.account;

import indi.mat.work.project.model.user.LoginUser;
import org.springframework.http.HttpMethod;

public interface IPermissionService {

    boolean hasPermission(String url, HttpMethod method, LoginUser user);
}
