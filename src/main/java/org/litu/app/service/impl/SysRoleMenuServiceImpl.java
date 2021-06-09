package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysRoleMenuMapper;
import org.litu.app.entity.system.SysRoleMenu;
import org.litu.app.service.ISysRoleMenuService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    public boolean save(String roleId, String... menuIds) {
        if (StringUtils.isBlank(roleId) || (menuIds != null && menuIds.length > 0 && StringUtils.isAnyBlank(menuIds))) {
            return false;
        }
        removeByRoleId(roleId);
        List<SysRoleMenu> entityList = new ArrayList<SysRoleMenu>();
        for (String menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            entityList.add(roleMenu);
        }
        return super.saveBatch(entityList);
    }

    /**
     * 根据roleId查找menuId
     *
     * @param roleId
     * @return
     */
    public List<String> roleMenuIds(String roleId) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isBlank(roleId)) {
            return result;
        }
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        for (SysRoleMenu roleMenu : list(queryWrapper)) {
            result.add(roleMenu.getMenuId());
        }
        return result;
    }

    /**
     * 删除角色 的权限
     *
     * @param roleId
     * @return
     */
    public boolean removeByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return false;
        }
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        return remove(queryWrapper);
    }
}
