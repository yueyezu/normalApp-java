package org.litu.app.dao;

import org.apache.ibatis.annotations.Param;
import org.litu.app.entity.system.SysMenu;
import org.litu.base.dao.BaseTreeMapper;

import java.util.List;

/**
 * <p>
 * 菜单Mapper 接口
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
public interface SysMenuMapper extends BaseTreeMapper<SysMenu> {
    List<SysMenu> userMenus(@Param("userId") String id, @Param("systemCode") String systemCode, @Param("menuTypes") List<String> menuTypes);

    List<SysMenu> userPrivileges(@Param("userId") String id, @Param("systemCode") String systemCode);
}
