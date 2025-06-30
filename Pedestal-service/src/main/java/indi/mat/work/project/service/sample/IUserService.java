package indi.mat.work.project.service.sample;

import indi.mat.work.project.model.sample.User;

import java.util.List;

public interface IUserService {

    public List<User> get();

    /**
     * 删除id用户
     * @param id
     */
    void delete(Long id);

    /**
     * 查询id用户，若有缓存查缓存
     * @param id
     * @return
     */
    User getById(Long id);


    Boolean save(User user);

}
