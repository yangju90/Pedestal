package indi.mat.work.project.service.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.mat.work.project.model.BaseModel;
import indi.mat.work.project.request.form.BaseForm;
import indi.mat.work.project.request.query.BaseQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseModel, F extends BaseForm, Q extends BaseQuery<T>> {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    public Long insert(T entity);

    /**
     * 插入一条记录
     *
     * @param form form
     */
    public Long insert(F form) ;

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    public int deleteById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    public int deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    public int delete(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    public int deleteBatchIds(Collection<? extends Serializable> idList);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    public int updateById(T entity);

    /**
     * 根据 ID 修改
     *
     * @param form 表单
     */
    public int updateById(F form);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    public int update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    public T selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    public List<T> selectBatchIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    public List<T> selectByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public T selectOne(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public Integer selectCount(Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public List<T> selectList(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public List<Object> selectObjs(Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <P extends IPage<T>> P selectPage(P page, Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     */
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<T> queryWrapper);

    /**
     * 根据 自定义查询 条件，查询全部记录（并翻页）
     *
     * @param query  自定义查询条件
     */
    public IPage<T>  queryPage(BaseQuery<T> query);

    /**
     * 根据 自定义查询 条件，查询全部记录
     *
     * @param query  自定义查询条件
     */
    public List<T> queryList(BaseQuery<T> query);

    /**
     * 根据 自定义查询 条件，查询单个对象
     *
     * @param query  自定义查询条件
     */
    public T queryOne(BaseQuery<T> query);

    /**
     * 根据model中的唯一索引的标识，校验数据是否已经存在，存在则抛出业务异常
     * @param t
     * @param isUpdate true: 更新操作，false:删除 操作
     */
    public void checkUnique(T t,boolean isUpdate);
}
