package org.litu.app.service;

import org.litu.app.entity.SysMenu;
import org.litu.base.service.IBaseTreeService;

import java.util.List;

/**
 * <p>
 * 菜单服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
public interface ISysMenuService extends IBaseTreeService<SysMenu> {
    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    public List<SysMenu> userMenus(String userId, String systemCode, List<String> menuTypes);

    /**
     * 获取用户权限code
     *
     * @param userId
     * @return
     */
    public List<String> userPrivileges(String userId, String systemCode);

}
