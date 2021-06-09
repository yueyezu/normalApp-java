package org.litu.app.service;

import org.litu.app.entity.system.SysUserLogin;
import org.litu.base.service.IBaseService;

/**
 * <p>
 *  用户登录服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
public interface ISysUserLoginService extends IBaseService<SysUserLogin> {
	
	
	/**
	 * 根据userId查找
	 * @param userId
	 * @return
	 */
	public SysUserLogin getByUserId(String userId);

}
