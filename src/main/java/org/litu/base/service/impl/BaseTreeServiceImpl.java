package org.litu.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.litu.base.config.BaseConstant;
import org.litu.base.dao.BaseTreeMapper;
import org.litu.base.entity.BaseTreeEntity;
import org.litu.base.service.IBaseTreeService;
import org.litu.core.exception.LtParamException;
import org.litu.util.common.FieldUtil;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * serviceimpl的基类
 *
 * @param <M>
 * @param <T>
 */
@Transactional
public abstract class BaseTreeServiceImpl<M extends BaseTreeMapper<T>, T extends BaseTreeEntity> extends BaseServiceImpl<M, T> implements IBaseTreeService<T> {

    /**
     * 树列表查询之前的方法
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @param params  Map参数
     * @param query   查询（ Entity 对象封装操作类）
     */
    public void beforeTree(T entity, String keyword, Map<String, String> params, LambdaQueryWrapper<T> query) {
        if (!StringUtils.isBlank(keyword)) {
            query.like(T::getfName, keyword);
        }
        if (!StringUtils.isBlank(entity.getfParentid())) {
            // 如果根据父类来进行查询的话，需要使用layer来进行比较
            query.like(T::getfLayers, entity.getfParentid());
        }
    }

    /**
     * 获取树列表信息
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @param params  Map参数
     * @return 书列表信息
     */
    @Override
    public List<T> tree(T entity, String keyword, Map<String, String> params) {
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
        beforeTree(entity, keyword, params, queryWrapper);

        List<T> trees = baseMapper.selectTree(queryWrapper);
        return trees;
    }

    /**
     * 保存
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true为保存成功
     */
    @Override
    public boolean save(T entity, Map<String, String> params) {
        if ("".equals(entity.getfParentid())) {
            entity.setfParentid("0");
        }

        boolean res = super.save(entity, params);
        res &= updateLayers(entity);

        if (!res) {
            throw new LtParamException("保存出现错误！");
        }
        return res;
    }

    /**
     * 更新
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true为更新成功
     */
    @Override
    public boolean update(T entity, Map<String, String> params) {
        if ("".equals(entity.getfParentid())) {
            entity.setfParentid("0");
        }

        boolean res = super.update(entity, params);
        res &= updateLayers(entity);

        if (!res) {
            throw new LtParamException("更新出现错误！");
        }

        return res;
    }

    /**
     * 删除之前的操作内容。 校验是否存在下级、校验是否允许删除
     *
     * @param id     需要删除的记录id
     * @param params Map参数
     * @param entity 实体类
     * @return true则为允许删除
     */
    @Override
    public boolean beforeDelete(String id, Map<String, String> params, T entity) {
        boolean result = super.beforeDelete(id, params, entity);

        LambdaQueryWrapper<T> query = Wrappers.lambdaQuery();
        query.like(T::getfLayers, entity.getfLayers() + "%");
        query.ne(T::getfId, id);
        if (CollectionUtils.isNotEmpty(list(query))) {
            throw new LtParamException("当前对象存在下级，请检查后再删除!");
        }

        return result;
    }

    /**
     * 逻辑删除之前的操作内容。
     *
     * @param id            需要逻辑删除的id
     * @param params        Map参数
     * @param entity        实体类
     * @param updateWrapper Update 条件封装
     * @return true为允许逻辑删除
     */
    @Override
    public boolean beforeLogicalDelete(String id, Map<String, String> params, T entity, UpdateWrapper<T> updateWrapper) {
        boolean result = super.beforeLogicalDelete(id, params, entity, updateWrapper);

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.like("F_Layers", entity.getfLayers() + "%");
        wrapper.eq("F_DeleteFlag", BaseConstant.FLAG_FALSE);
        wrapper.ne("F_Id", id);
        if (CollectionUtils.isNotEmpty(list(wrapper))) {
            throw new LtParamException("当前对象存在下级，请检查后再删除!");
        }

        return result;
    }

    /**
     * 更新layer字段
     *
     * @param entity 实体类
     * @return true为更新成功
     */
    protected boolean updateLayers(T entity) {
        String oldLayers = FieldUtil.read(entity, "fLayers");
        String layers = "";
        if (StringUtils.isNotBlank(FieldUtil.read(entity, "fParentid"))) {
            T parent = getById(FieldUtil.read(entity, "fParentid"));
            if (parent != null) {
                layers = FieldUtil.read(parent, "fLayers");
            }
        }
        if (StringUtils.isBlank(layers)) {
            layers = "#" + entity.getfId() + "#";
        } else {
            layers += "|#" + entity.getfId() + "#";
        }
        LambdaUpdateWrapper<T> updateWrapper = Wrappers.lambdaUpdate();
        if (StringUtils.isNotBlank(oldLayers)) {
            updateWrapper.setSql(MessageFormat.format("F_Layers = REPLACE(F_Layers,''{0}'',''{1}'')", oldLayers, layers));
            updateWrapper.like(T::getfLayers, oldLayers + "%");
        } else {
            updateWrapper.set(T::getfLayers, layers);
            updateWrapper.eq(T::getfId, entity.getfId());
        }
        try {
            return update(getEntityClass().newInstance(), updateWrapper);
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        }
    }
}
