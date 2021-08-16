package indi.mat.work.project.service.system;

import indi.mat.work.project.model.sample.User;

public interface SystemMenuService {

    public void delete(Integer id);

    public void deleteName(String name);

    public User getById(Integer id);

    public User get(String name);

}
