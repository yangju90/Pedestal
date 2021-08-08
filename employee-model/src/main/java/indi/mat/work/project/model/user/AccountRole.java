package indi.mat.work.project.model.user;

import indi.mat.work.project.model.BaseModel;

public class AccountRole extends BaseModel {
    private Long roleId;
    private String menuCode;
    private Integer functionCode;

    public AccountRole(){}

    public AccountRole(Long roleId, String menuCode, Integer functionCode) {
        this.roleId = roleId;
        this.menuCode = menuCode;
        this.functionCode = functionCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Integer functionCode) {
        this.functionCode = functionCode;
    }
}
