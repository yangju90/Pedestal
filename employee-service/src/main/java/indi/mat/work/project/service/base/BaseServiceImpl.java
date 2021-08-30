package indi.mat.work.project.service.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.mat.work.project.model.BaseModel;
import indi.mat.work.project.request.form.BaseForm;
import indi.mat.work.project.request.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<T extends BaseModel, F extends BaseForm, Q extends BaseQuery> implements BaseService{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BaseMapper<T> mapper;

    @Override
    public Integer insert(BaseModel baseModel) {
        return mapper.insert((T) baseModel);
    }
}
