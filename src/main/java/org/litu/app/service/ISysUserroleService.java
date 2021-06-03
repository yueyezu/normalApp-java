package org.litu.app.service;

import java.util.List;

import org.litu.app.entity.system.SysUserrole;
import org.litu.app.entity.vo.UserRole;
import org.litu.base.service.IBaseService;

/**
 * <p>
 * 用户角色服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
public interface ISysUserroleService extends IBaseService<SysUserrole> {

	/**
	 * 根据userId查找
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysUserrole> getByUserId(String userId);

	/**
	 * 用户角色选项
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserRole> userRoles(String userId);

	/**
	 * 更新角色权限
	 * 
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	public boolean save(String userId, String... roleIds);

	/**
	 * 删除角色 的权限
	 * 
	 * @param userId
	 * @return
	 */
	public boolean removeByUserId(String userId);

	/**
	 * 根据权限id删除
	 * 
	 * @param menuId
	 * @return
	 */
	public boolean removeByRole(String roleId);

}
