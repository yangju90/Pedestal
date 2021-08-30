package indi.mat.work.project.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import indi.mat.work.project.model.BaseModel;

import java.util.Date;

@TableName("indi_system_menu")
public class SystemMenu extends BaseModel {

    // 系统菜单编码
    private String menuCode;
    private String name;

    // 父级菜单编码
    private String pCode;

    // 菜单状态 true 启用 false 未启用
    private boolean menuStatus;

    // 菜单控制的链接
    private String href;
    private String path;
    private String icon;


    private String urlPrefix;
    private Integer sort;

    // 菜单description
    private String content;


    public SystemMenu() {
    }

    public SystemMenu(String menuCode, String name, String pCode, boolean menuStatus, String href, String path, String icon, String urlPrefix, Integer sort, String content, boolean deleted, String opUser,Date opDate) {
        this.menuCode = menuCode;
        this.name = name;
        this.pCode = pCode;
        this.menuStatus = menuStatus;
        this.href = href;
        this.path = path;
        this.icon = icon;
        this.urlPrefix = urlPrefix;
        this.sort = sort;
        this.content = content;
        this.setDeleted(deleted);
        this.setOpUser(opUser);
        this.setOpDate(opDate);
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public boolean isMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(boolean menuStatus) {
        this.menuStatus = menuStatus;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
