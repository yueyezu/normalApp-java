package org.litu.app.service;

import org.litu.app.entity.SysUserlogin;
import org.litu.base.service.IBaseService;

/**
 * <p>
 *  用户登录服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
public interface ISysUserloginService extends IBaseService<SysUserlogin> {
	
	
	/**
	 * 根据userId查找
	 * @param userId
	 * @return
	 */
	public SysUserlogin getByUserId(String userId);

}
