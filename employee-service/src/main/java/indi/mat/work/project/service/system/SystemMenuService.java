package indi.mat.work.project.service.system;

import indi.mat.work.project.model.sample.User;
import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.service.base.BaseService;

import java.util.List;

public interface SystemMenuService extends BaseService {

    public void delete(Integer id);

    public void deleteName(String name);

    public SystemMenu getById(Long id);

    public User get(String name);

    public List<SystemMenu> selectAll();

}
