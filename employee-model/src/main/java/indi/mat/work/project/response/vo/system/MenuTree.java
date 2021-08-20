package indi.mat.work.project.response.vo.system;

import java.util.List;

public class MenuTree {
    private String path;
    private String icon;
    private String menuName;
    private int sort;

    private List<MenuTree> subTree;

    public MenuTree(){}

    public MenuTree(String path, String icon, String menuName, int sort){
        this.path = path;
        this.icon = icon;
        this.menuName = menuName;
        this.sort = sort;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<MenuTree> getSubTree() {
        return subTree;
    }

    public void setSubTree(List<MenuTree> subTree) {
        this.subTree = subTree;
    }
}
