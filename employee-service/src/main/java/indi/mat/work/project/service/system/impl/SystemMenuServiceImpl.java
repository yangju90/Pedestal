package indi.mat.work.project.service.system.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.mat.work.project.dao.system.SystemMenuMapper;
import indi.mat.work.project.model.sample.User;
import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.request.form.system.SystemMenuForm;
import indi.mat.work.project.request.query.system.SystemMenuQuery;
import indi.mat.work.project.service.base.BaseServiceImpl;
import indi.mat.work.project.service.system.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenu, SystemMenuForm, SystemMenuQuery> implements ISystemMenuService {

    @Autowired
    SystemMenuMapper mapper;

    @Override
    protected BaseMapper<SystemMenu> mapper() {
        return mapper;
    }

    @Override
    protected SystemMenu model() {
        return new SystemMenu();
    }


    @CacheEvict(value = "user", key = "#id")
    public void delete(Integer id) {
        System.out.println("system删除key为[" + id + "]的缓存");
    }

    @CacheEvict(value = "user", key = "#name")
    public void deleteName(String name) {
        System.out.println("system删除key为[" + name + "]的缓存");
    }

    @Cacheable(value = "user", key = "#result.name", condition="#result != null && #result.id != null")
    public SystemMenu getById(Long id) {
        System.out.println("system操作数据库，进行通过ID查询，ID: " + id);
        return mapper.selectById(id);
    }

    @Cacheable(value = "user", key = "#name")
    public User get(String name) {
        System.out.println("system操作数据库，进行通过name查询，Name: " + name);
        return new User(1, "admin", "123", 18);
    }

    @Override
    public List<SystemMenu> selectAll() {
        return mapper.selectList(null);
    }



}
