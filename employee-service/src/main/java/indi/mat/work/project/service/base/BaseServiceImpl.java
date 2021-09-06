package indi.mat.work.project.service.base;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import indi.mat.work.project.annotation.Unique;
import indi.mat.work.project.exception.EmployeeException;
import indi.mat.work.project.model.BaseModel;
import indi.mat.work.project.request.form.BaseForm;
import indi.mat.work.project.request.query.BaseQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<T extends BaseModel, F extends BaseForm, Q extends BaseQuery<T>> implements BaseService<T, F, Q> {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected abstract BaseMapper<T> mapper();

    protected abstract T model();

    @Autowired
    private ApplicationContext context;

    /**
     * 发布事件
     *
     * @param event
     */
    protected void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    @Override
    public Long insert(T entity) {
        mapper().insert(entity);
        return entity.getId();
    }

    /**
     * 插入一条记录
     *
     * @param form form
     */
    @Override
    public Long insert(F form) {
        T model = model();
        BeanUtils.copyProperties(form, model);
        return insert(model);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Override
    public int deleteById(Serializable id) {

        if (id == null) {
            throw new EmployeeException("id is must not null");
        }

        if (selectById(id) == null) {
            throw new EmployeeException("id not exists");
        }

        return mapper().deleteById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return mapper().deleteByMap(columnMap);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    @Override
    public int delete(Wrapper<T> queryWrapper) {
        return mapper().delete(queryWrapper);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            if (selectById(id) == null) {
                throw new EmployeeException("id not exists");
            }
        }
        return mapper().deleteBatchIds(idList);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    @Override
    public int updateById(T entity) {
        if (entity.getId() == null) {
            throw new EmployeeException("id is required");
        }

        if (selectById(entity.getId()) == null) {
            throw new EmployeeException("id not exists");
        }
        return mapper().updateById(entity);
    }

    /**
     * 根据 ID 修改
     *
     * @param form 表单
     */
    @Override
    public int updateById(F form) {
        T model = model();
        BeanUtils.copyProperties(form, model);
        return updateById(model);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    @Override
    public int update(T entity, Wrapper<T> updateWrapper) {
        return mapper().update(entity, updateWrapper);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    @Override
    public T selectById(Serializable id) {
        return mapper().selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return mapper().selectBatchIds(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        return mapper().selectByMap(columnMap);
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public T selectOne(Wrapper<T> queryWrapper) {
        return mapper().selectOne(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public Integer selectCount(Wrapper<T> queryWrapper) {
        return mapper().selectCount(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        return mapper().selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
        return mapper().selectMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public List<Object> selectObjs(Wrapper<T> queryWrapper) {
        return mapper().selectObjs(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Override
    public <P extends IPage<T>> P selectPage(P page, Wrapper<T> queryWrapper) {
        return mapper().selectPage(page, queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     */
    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<T> queryWrapper) {
        return mapper().selectMapsPage(page, queryWrapper);
    }

    /**
     * 根据 自定义查询 条件，查询全部记录（并翻页）
     *
     * @param query 自定义查询条件
     */
    @Override
    public IPage<T> queryPage(BaseQuery<T> query) {
        return mapper().selectPage(query.page(), query.wrapper());
    }

    /**
     * 根据 自定义查询 条件，查询全部记录
     *
     * @param query 自定义查询条件
     */
    @Override
    public List<T> queryList(BaseQuery<T> query) {
        return mapper().selectList(query.wrapper());
    }

    /**
     * 根据 自定义查询 条件，查询单个对象
     *
     * @param query 自定义查询条件
     */
    @Override
    public T queryOne(BaseQuery<T> query) {
        return mapper().selectOne(query.wrapper());
    }

    /**
     * check uniqueValue  if  exists
     *
     * @param t
     * @return
     * @throws Exception
     */
    @Override
    public synchronized void checkUnique(T t, boolean isUpdate) {
        try {
            //index 0 is  primary key column name ;index 1 primary key value
            Object[] pkInfo = new Object[2];
            if (isUpdate) {
                pkInfo = getPrimaryKeyInfo(t);
            }
            Field[] fields = t.getClass().getDeclaredFields();
            ;
            for (Field field : fields) {
                field.setAccessible(true);
                boolean isUniqueField = field.isAnnotationPresent(Unique.class);
                if (isUniqueField) {
                    String uniqueColumnName = getColumnName(field);
                    Object uniqueValue = invokeGetMethod(t, field.getName());
                    checkOneUnique(pkInfo, uniqueColumnName, uniqueValue);
                }
            }
        } catch (EmployeeException e) {
            throw e;
        } catch (Exception e) {
            logger.error(" checkUnique is error need check page or implementation you self ", e);
        }

    }

    /**
     * PrimaryKeyInfo
     *
     * @param t
     * @return //index 0 is  primary key column name ; index 1 primary key value
     * @throws Exception
     */
    private Object[] getPrimaryKeyInfo(T t) throws Exception {
        Object[] result = new Object[2];
        Field[] fields = t.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            boolean isIdField = field.isAnnotationPresent(TableId.class);
            if (isIdField) {
                result[0] = getColumnName(field);
                result[1] = invokeGetMethod(t, field.getName());
                break;
            }
        }
        return result;
    }


    /**
     * check uniqueValue always exists
     *
     * @param pkInfo
     * @param uniqueColumn
     * @param uniqueValue
     */
    private void checkOneUnique(Object[] pkInfo, String uniqueColumn, Object uniqueValue) {
        String pkColumnName = pkInfo[0] == null ? null : pkInfo[0].toString();
        Object pkValue = pkInfo[1];
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(uniqueColumn, uniqueValue);
        if (pkColumnName == null) {
            pkColumnName = "id";
        }
        if (pkValue != null) {
            queryWrapper.ne(pkColumnName, pkValue);
        }
        queryWrapper.eq("deleted", 0);
        T t = selectOne(queryWrapper);
        if (t != null) {
            throw new EmployeeException(StringUtils.underlineToCamel(uniqueColumn) + ":" + uniqueValue + " is already in use");
        }
    }

    private Object invokeGetMethod(T t, String fieldName) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, t.getClass());
        //获得读方法
        Method readMethod = pd.getReadMethod();
        Object value = readMethod.invoke(t);
        return value;
    }

    private String getColumnName(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        String columnName = "";
        if (tableField != null) {
            columnName = tableField.value();
        } else {
            columnName = StringUtils.camelToUnderline(field.getName());
        }
        return columnName;
    }

}
