package org.litu.base.service;

import org.litu.core.base.BaseTreeEntity;

import java.util.List;
import java.util.Map;

/**
 * service的基类
 *
 * @param <T>
 */
public interface IBaseTreeService<T extends BaseTreeEntity> extends IBaseService<T> {

	/**
	 * 获取树列表信息
	 * @param entity 实体类
	 * @param keyword 关键词
	 * @param params Map参数
	 * @return 树列表
	 */
	List<T> tree(T entity, String keyword, Map<String, String> params);

}
