package org.litu.app.service.impl;

import java.util.List;

import org.litu.app.dao.SysConfigsMapper;
import org.litu.app.entity.system.SysConfigs;
import org.litu.app.service.ISysConfigsService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-29
 */
@Service
public class SysConfigsServiceImpl extends BaseServiceImpl<SysConfigsMapper, SysConfigs> implements ISysConfigsService {

	@Override
	public boolean updates(List<SysConfigs> entity) {
		return updateBatchById(entity);
	}
}
