package indi.mat.work.project.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import indi.mat.work.project.model.BaseModel;

@TableName("indi_system_menu")
public class SystemMenu extends BaseModel {

    // 系统菜单编码
    private String menuCode;
    private String menuName;

    // 父级菜单编码
    private String parentCode;

    // 菜单状态 true 启用 false 未启用
    private boolean menuStatus;

    // 菜单控制的链接
    private String herf;
    private Integer sort;
    private String icon;

    // 菜单description
    private String memo;

    public SystemMenu(){}

    public SystemMenu(String menuCode, String menuName, String parentCode, boolean menuStatus, String herf, Integer sort, String icon, String memo){
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.parentCode = parentCode;
        this.menuStatus = menuStatus;
        this.herf = herf;
        this.sort = sort;
        this.icon = icon;
        this.memo = memo;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(boolean menuStatus) {
        this.menuStatus = menuStatus;
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
