package indi.mat.work.project.request.query.system;

import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.request.query.BaseQuery;
import org.apache.commons.lang3.StringUtils;

public class SystemMenuQuery extends BaseQuery<SystemMenu> {

    private String name;

    @Override
    public void setCustomerCondition() {
        if(!StringUtils.isBlank(name)){
            lambdaWrapper().like(SystemMenu::getName, name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
