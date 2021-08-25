package indi.mat.work.project.service.system.impl;

import indi.mat.work.project.annotation.Favorite;
import indi.mat.work.project.service.system.SystemMenuService;
import indi.mat.work.project.model.sample.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @CacheEvict(value = "user", key = "#id")
    public void delete(Integer id) {
        System.out.println("system删除key为[" + id + "]的缓存");
    }

    @CacheEvict(value = "user", key = "#name")
    public void deleteName(String name) {
        System.out.println("system删除key为[" + name + "]的缓存");
    }

    @Cacheable(value = "user", key = "#result.name", condition="#result != null && #result.id != null")
    public User getById(Integer id) {
        System.out.println("system操作数据库，进行通过ID查询，ID: " + id);
        return new User(id, "admin", "123", 18);
    }

    @Cacheable(value = "user", key = "#name")
    public User get(String name) {
        System.out.println("system操作数据库，进行通过name查询，Name: " + name);
        return new User(1, "admin", "123", 18);
    }

}
