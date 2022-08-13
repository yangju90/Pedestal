package indi.mat.work.project.service.system;

import indi.mat.work.project.model.sample.User;
import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.request.form.system.SystemMenuForm;
import indi.mat.work.project.request.query.system.SystemMenuQuery;
import indi.mat.work.project.service.base.BaseService;

import java.util.List;

public interface ISystemMenuService extends BaseService<SystemMenu, SystemMenuForm, SystemMenuQuery> {

    void delete(Integer id);

    void deleteName(String name);

    SystemMenu getById(Long id);

    User get(String name);

    List<SystemMenu> selectAll();

}
