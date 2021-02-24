package org.litu.base.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.litu.core.base.BaseTreeEntity;

import java.util.List;

/**
 * 树mapper的基类
 *
 * @param <T>
 */
public interface BaseTreeMapper<T extends BaseTreeEntity> extends BaseMapper<T> {
    /**
     * 数结构的选择方法
     *
     * @param queryWrapper entity对象的封装操作类实例
     * @return 树列表信息
     */
    public List<T> selectTree(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
