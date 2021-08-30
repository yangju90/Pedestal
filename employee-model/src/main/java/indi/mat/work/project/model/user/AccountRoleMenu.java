package indi.mat.work.project.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import indi.mat.work.project.model.BaseModel;

@TableName("indi_account_role")
public class AccountRoleMenu extends BaseModel {
    private Long roleId;
    private String menuCode;
    private Integer functionCode;

    public AccountRoleMenu(){}

    public AccountRoleMenu(Long roleId, String menuCode, Integer functionCode) {
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
