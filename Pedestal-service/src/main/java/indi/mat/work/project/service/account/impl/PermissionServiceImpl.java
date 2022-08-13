package indi.mat.work.project.service.account.impl;

import indi.mat.work.project.model.user.LoginUser;
import indi.mat.work.project.service.account.IPermissionService;
import indi.mat.work.project.service.base.BaseServiceImpl;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Override
    public boolean hasPermission(String url, HttpMethod method, LoginUser user) {
        return true;
    }
}
