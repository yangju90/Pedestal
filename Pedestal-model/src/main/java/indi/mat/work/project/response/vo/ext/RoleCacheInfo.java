package indi.mat.work.project.response.vo.ext;

import indi.mat.work.project.response.vo.system.MenuTree;
import indi.mat.work.project.response.vo.user.RolePermissionInfo;

import java.util.List;

public class RoleCacheInfo {
    private Long roleId;
    private List<RolePermissionInfo> rolePermissionInfoList;
    private MenuTree menuTree;

    public RoleCacheInfo() {
    }

    public RoleCacheInfo(Long roleId, List<RolePermissionInfo> rolePermissionInfoList, MenuTree menuTree) {
        this.roleId = roleId;
        this.rolePermissionInfoList = rolePermissionInfoList;
        this.menuTree = menuTree;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<RolePermissionInfo> getRolePermissionInfoList() {
        return rolePermissionInfoList;
    }

    public void setRolePermissionInfoList(List<RolePermissionInfo> rolePermissionInfoList) {
        this.rolePermissionInfoList = rolePermissionInfoList;
    }

    public MenuTree getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(MenuTree menuTree) {
        this.menuTree = menuTree;
    }
}
