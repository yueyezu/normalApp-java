package org.litu.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.litu.base.config.BaseConstant;
import org.litu.base.dao.BaseMapper;
import org.litu.base.entity.BaseEntity;
import org.litu.base.service.IBaseService;
import org.litu.base.service.IBaseUserService;
import org.litu.base.util.UserUtil;
import org.litu.core.exception.LtParamException;
import org.litu.util.common.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * serviceimpl的基类
 *
 * @param <M>
 * @param <T>
 */
@SuppressWarnings({"unchecked"})
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Autowired
    IBaseUserService baseUserService;

    /**
     * 列表查询参数的处理方法
     */
    public void beforeList(T entity, String keyword, Map<String, String> params, LambdaQueryWrapper<T> query) {
        // query.eq("F_DeleteFlag", BaseConstant.FLAG_FALSE);
        // query.like("F_Code", keyword);
        // query.like("F_Name", keyword);
    }

    /**
     * controller 列表方法
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @param params  参数
     * @return controller层列表方法
     */
    @Override
    public List<T> list(T entity, String keyword, Map<String, String> params) {
        LambdaQueryWrapper<T> query = Wrappers.lambdaQuery();
        beforeList(entity, keyword, params, query);
        List<T> lists = list(query);
        return lists;
    }

    /**
     * 分页查询前的处理方法
     */
    public void beforePage(T entity, String keyword, IPage<T> page, Map<String, String> params, LambdaQueryWrapper<T> query) {
        // query.eq("F_DeleteFlag", BaseConstant.FLAG_FALSE);
        // query.like("F_Code", keyword);
        // query.like("F_Name", keyword);
    }

    /**
     * controller 分页查询方法
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @param page    分页信息
     * @param params  参数
     * @return controller分页
     */
    @Override
    public IPage<T> page(T entity, String keyword, IPage<T> page, Map<String, String> params) {
        LambdaQueryWrapper<T> query = Wrappers.lambdaQuery();
        beforePage(entity, keyword, page, params, query);
        IPage<T> pages = page(page, query);

        // 这里特殊处理下，如果获取的页没有数据，并且不是第一页的话，获取最后一页的数据
        if (!pages.getRecords().isEmpty() || pages.getCurrent() == 1l) {
            return pages;
        }
        page.setCurrent(pages.getPages());
        pages = page(page, query);
        return pages;
    }

    /**
     * 获取实体详细信息
     *
     * @param id 需要获取详细信息的实体id
     * @return 实体的详细信息
     */
    @Override
    public T detail(String id) {
        T one = getById(id);

        // 这里转化只有在查询时才会使用到
        // 将创建人ID转为名称
        if (FieldUtil.hasProperty(one.getClass(), "fCreateby")) {
            String uid = FieldUtil.read(one, "fCreateby");
            String name = baseUserService.getNameByUserId(uid);
            FieldUtil.write(one, "fCreateby", name);
        }
        // 将修改人ID转为名称
        if (FieldUtil.hasProperty(one.getClass(), "fModifyby")) {
            String uid = FieldUtil.read(one, "fModifyby");
            String name = baseUserService.getNameByUserId(uid);
            FieldUtil.write(one, "fModifyby", name);
        }
        return one;
    }

    /**
     * 保存之前的方法
     *
     * @param entity 实体类
     * @param params 参数
     * @return true表示信息保存成功
     */
    public boolean beforeSave(T entity, Map<String, String> params) {
        if (FieldUtil.hasProperty(entity.getClass(), "fCreatetime")) {
            FieldUtil.write(entity, "fCreatetime", new Date());
            FieldUtil.write(entity, "fCreateby", UserUtil.getUserId());
        }
        if (FieldUtil.hasProperty(entity.getClass(), "fModifytime")) {
            FieldUtil.write(entity, "fModifytime", new Date());
            FieldUtil.write(entity, "fModifyby", UserUtil.getUserId());
        }
        if (FieldUtil.hasProperty(entity.getClass(), "fEnabledelete")) {
            FieldUtil.write(entity, "fEnabledelete", BaseConstant.FLAG_TRUE);
        }
        if (FieldUtil.hasProperty(entity.getClass(), "fDeleteflag")) {
            FieldUtil.write(entity, "fDeleteflag", BaseConstant.FLAG_FALSE);
        }
        return true;
    }

    /**
     * 保存
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true表明保存成功
     */
    @Override
    public boolean save(T entity, Map<String, String> params) {
        boolean before = beforeSave(entity, params);
        if (before) {
            return super.save(entity);
        } else {
            return false;
        }
    }

    /**
     * 更新之前的操作
     *
     * @param entity        实体类
     * @param params        Map参数
     * @param updateWrapper Update 条件封装，用于Entity对象更新操作
     * @return true表示成功更新之前的操作
     */
    public boolean beforeUpdate(T entity, Map<String, String> params, LambdaUpdateWrapper<T> updateWrapper) {
        if (FieldUtil.hasProperty(entity.getClass(), "fModifytime")) {
            FieldUtil.write(entity, "fModifytime", new Date());
            FieldUtil.write(entity, "fModifyby", UserUtil.getUserId());
        }

        return true;
    }

    /**
     * 更新
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true则表明更新成功
     */
    @Override
    public boolean update(T entity, Map<String, String> params) {
        if (getById(entity.getfId()) == null) {
            throw new LtParamException("无效的ID!");
        }

        LambdaUpdateWrapper<T> updateWrapper = Wrappers.lambdaUpdate();
        if (!beforeUpdate(entity, params, updateWrapper)) {
            throw new LtParamException("更新数据存在问题!");
        }

        try {
            if (updateWrapper.isEmptyOfWhere()) {
                return updateById(entity);
            } else {
                return update(getEntityClass().newInstance(), updateWrapper);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println(e.toString());
            return false;
        }
    }

    /**
     * 物理删除之前的操作内容。 校验是否允许删除
     *
     * @param id     需要删除的id
     * @param params Map参数
     * @param entity 实体类
     * @return true则表明允许删除
     */
    public boolean beforeDelete(String id, Map<String, String> params, T entity) {
        if (FieldUtil.hasProperty(entity.getClass(), "fEnabledelete")) {
            if (FieldUtil.<Integer>read(entity, "fEnabledelete").equals(BaseConstant.FLAG_FALSE)) {
                throw new LtParamException("当前对象，不允许删除!");
            }
        }

        return true;
    }

    /**
     * 删除(物理删除)
     *
     * @param id     需要删除的记录id
     * @param params Map参数
     * @return true则表明删除成功
     */
    @Override
    public boolean delete(String id, Map<String, String> params) {
        T entity = getById(id);
        if (entity == null) {
            throw new LtParamException("无效的ID!");
        }

        boolean res = beforeDelete(id, params, entity);

        if (res == false) {
            throw new LtParamException("删除验证不通过，无法进行删除");
        }
        // 物理删除
        return removeById(id);
    }

    /**
     * 物理删除之前的操作内容。 校验是否允许删除
     *
     * @param idList       需要批量删除的id列表
     * @param params       Map参数
     * @param queryWrapper Entity 对象封装操作类
     * @return true表明允许删除
     */
    public boolean beforeBatchDelete(List<String> idList, Map<String, String> params, LambdaQueryWrapper<T> queryWrapper) {
        queryWrapper.in(T::getfId, idList);

        return true;
    }

    /**
     * 批量物理删除
     *
     * @param idList 需要删除的id列表
     * @param params Map参数
     * @return true则表明删除成功
     */
    @Override
    public boolean batchDelete(List<String> idList, Map<String, String> params) {
        LambdaQueryWrapper<T> query = Wrappers.lambdaQuery();
        boolean res = beforeBatchDelete(idList, params, query);
        if (!res) {
            throw new LtParamException("删除验证不通过，无法进行删除！");
        }
        return baseMapper.delete(query) > 0;
    }

    /**
     * 逻辑删除之前的操作内容。
     *
     * @param id     需要逻辑删除的id
     * @param params Map参数
     * @param entity 实体类
     * @return true则表明允许逻辑删除
     */
    public boolean beforeLogicalDelete(String id, Map<String, String> params, T entity, UpdateWrapper<T> updateWrapper) {
        if (FieldUtil.hasProperty(entity.getClass(), "fEnabledelete")) {
            if (FieldUtil.<Integer>read(entity, "fEnabledelete").equals(BaseConstant.FLAG_FALSE)) {
                throw new LtParamException("当前对象，不允许删除!");
            }
        }

        updateWrapper.eq("F_id", id);
        updateWrapper.set("F_DeleteFlag", BaseConstant.FLAG_TRUE);
        updateWrapper.set("F_DeleteUserId", UserUtil.getUserId());
        updateWrapper.set("F_DeleteTime", new Date());

        return true;
    }

    /**
     * 逻辑删除的方法
     *
     * @param id     逻辑删除的id
     * @param params Map参数
     * @return true则表明逻辑删除成功
     */
    @Override
    public boolean logicalDelete(String id, Map<String, String> params) {
        T entity = getById(id);
        if (entity == null) {
            throw new LtParamException("无效的ID!");
        }

        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        boolean res = beforeLogicalDelete(id, params, entity, updateWrapper);
        if (res == false) {
            throw new LtParamException("删除验证不通过，无法进行删除");
        }

        try {
            return update(getEntityClass().newInstance(), updateWrapper);
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 逻辑恢复
     *
     * @param id
     * @param params
     * @param entity
     * @param updateWrapper
     * @return
     */
    public boolean beforeLogicalRestore(String id, Map<String, String> params, T entity, UpdateWrapper<T> updateWrapper) {
        updateWrapper.eq("F_id", id);
        updateWrapper.set("F_DeleteFlag", BaseConstant.FLAG_FALSE);
        updateWrapper.set("F_DeleteUserId", null);
        updateWrapper.set("F_DeleteTime", null);
        return true;
    }

    /**
     * 逻辑恢复
     *
     * @param id
     * @param params
     * @return
     */
    @Override
    public boolean logicalRestore(String id, Map<String, String> params) {
        T entity = getById(id);
        if (entity == null) {
            throw new LtParamException("无效的ID!");
        }

        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        boolean res = beforeLogicalRestore(id, params, entity, updateWrapper);
        if (res == false) {
            throw new LtParamException("恢复验证不通过，无法进行删除");
        }

        try {
            return update(getEntityClass().newInstance(), updateWrapper);
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 是否存在
     *
     * @param id    需要检查是否存在的id
     * @param value 值
     * @param field 要检查的字段
     * @return true则表明存在
     */
    @Override
    public boolean exists(String id, String value, String field) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 排除掉当前数据
        if (StringUtils.isNotBlank(id)) {
            queryWrapper.ne("F_Id", id);
        }
        queryWrapper.eq(field, value);
        T entity = getOne(queryWrapper);
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置单个字段的值
     *
     * @param id    数据的id
     * @param value 需要设置的值
     * @param field 字段名称
     * @return
     */
    @Override
    public boolean changeValue(String id, String value, String field) {
        T entity = getById(id);
        if (entity == null) {
            throw new LtParamException("id不存在!");
        }

        UpdateWrapper<T> query = new UpdateWrapper<>();
        query.eq("F_Id", id);
        query.set(field, value);
        if (FieldUtil.hasProperty(entity.getClass(), "fModifytime")) {
            FieldUtil.write(entity, "fModifytime", new Date());
            FieldUtil.write(entity, "fModifyby", UserUtil.getUserId());
        }

        return update(query);
    }

    /* ***************** 服务层公共的方法 ********************** */

    /**
     * 获取泛型的实际类型
     *
     * @return 泛型实际类型
     */
    protected Class<?>[] getTypes() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        Class<?>[] result = new Class<?>[2];
        result[0] = (Class<?>) types[0];
        result[1] = (Class<?>) types[1];
        return result;
    }

    /**
     * 获取运行期BaseMapper泛型类型的真实类型
     *
     * @return 运行期BaseMapper泛型类型的真实类型
     */
    protected Class<M> getMapperClass() {
        return (Class<M>) getTypes()[0];
    }

    /**
     * 获取运行期BaseEntityh泛型类型的真实类型
     *
     * @return 运行期BaseEntityh泛型类型的真实类型
     */
    protected Class<T> getEntityClass() {
        return (Class<T>) getTypes()[1];
    }
}
