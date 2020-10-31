package org.litu.app.service;

import java.util.List;

import org.litu.app.entity.SysConfigs;
import org.litu.base.service.IBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-29
 */
public interface ISysConfigsService extends IBaseService<SysConfigs> {
	public boolean updates(List<SysConfigs> entity);

}
