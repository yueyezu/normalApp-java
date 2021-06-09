package org.litu.app.service;

import java.util.List;

import org.litu.app.entity.system.SysRoleMenu;
import org.litu.base.service.IBaseService;

/**
 * <p>
 * 角色菜单服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
public interface ISysRoleMenuService extends IBaseService<SysRoleMenu> {

	/**
	 * 更新角色权限
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public boolean save(String roleId, String... menuIds);

	/**
	 * 根据roleId查找menuId
	 *
	 * @param roleId
	 * @return
	 */
	public List<String> roleMenuIds(String roleId);

	/**
	 * 删除角色 的权限
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean removeByRoleId(String roleId);

}
