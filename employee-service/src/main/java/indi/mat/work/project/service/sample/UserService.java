package indi.mat.work.project.service.sample;

import indi.mat.work.project.sample.bean.User;

public interface UserService {

    /**
     * 删除id用户
     * @param id
     */
    public void delete(Integer id);

    /**
     * 查询id用户，若有缓存查缓存
     * @param id
     * @return
     */
    public User getById(Integer id);

}
