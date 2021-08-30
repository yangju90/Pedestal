package indi.mat.work.project.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.mat.work.project.model.system.SystemFavoritesInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemFavoritesInfoMapper extends BaseMapper<SystemFavoritesInfo> {
}
