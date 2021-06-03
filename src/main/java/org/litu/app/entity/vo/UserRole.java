package org.litu.app.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.beanutils.BeanUtils;
import org.litu.app.entity.system.SysRole;

import java.lang.reflect.InvocationTargetException;

/**
 * 用户角色
 *
 */
@ApiModel(value = "用户角色",description = "继承SysRole")
public class UserRole extends SysRole {

	private static final long serialVersionUID = 734872974014125254L;
	@ApiModelProperty(value = "是否被选中",required = true)
	private boolean checked = false;
	@ApiModelProperty(value = "角色ID",required = true)
	private String roleId;
	@ApiModelProperty(value = "角色名字",required = true)
	private String roleName;

	/**
	 * 初始化
	 * @param role 角色实例
	 */
	public void init(SysRole role) {
		if (role != null) {
			try {
				BeanUtils.copyProperties(this, role);
				setRoleId(getId());
				setRoleName(getName());
			} catch (IllegalAccessException | InvocationTargetException e) {
			}
		}
	}

	/**
	 * 获取是否被选中
	 * @return 是否被选中
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * 设置是否被选中
	 * @param checked  是否被选中
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 获取角色ID
	 * @return 角色id
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 设置角色ID
	 * @param roleId 角色ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取角色名字
	 * @return 角色名字
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置角色名字
	 * @param roleName 角色名字
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}