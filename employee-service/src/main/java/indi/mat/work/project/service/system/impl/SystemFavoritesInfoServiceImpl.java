package indi.mat.work.project.service.system.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.mat.work.project.dao.system.SystemFavoritesInfoMapper;
import indi.mat.work.project.model.system.SystemFavoritesInfo;
import indi.mat.work.project.request.form.system.SystemFavoritesInfoForm;
import indi.mat.work.project.request.query.system.SystemFavoritesInfoQuery;
import indi.mat.work.project.service.base.BaseServiceImpl;
import indi.mat.work.project.service.system.ISystemFavoritesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemFavoritesInfoServiceImpl extends BaseServiceImpl<SystemFavoritesInfo, SystemFavoritesInfoForm, SystemFavoritesInfoQuery> implements ISystemFavoritesInfoService {

    @Autowired
    SystemFavoritesInfoMapper mapper;
    
    @Override
    protected BaseMapper<SystemFavoritesInfo> mapper() {
        return mapper;
    }

    @Override
    protected SystemFavoritesInfo model() {
        return new SystemFavoritesInfo();
    }

    @Override
    public List<SystemFavoritesInfo> selectAll() {
        return mapper.selectList(null);
    }

    @Override
    public List<SystemFavoritesInfo> getBusinessTypeFavorites(String businessFavoritesType, Long accountId) {
        return null;
    }
}
