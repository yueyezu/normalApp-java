package org.litu.base.dao;

import org.litu.base.entity.BaseEntity;

/**
 * mapper的基类
 *
 * @param <T>
 */
public interface BaseMapper<T extends BaseEntity> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>{

}
