package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysMenuMapper;
import org.litu.app.dao.SysRolemenuMapper;
import org.litu.app.entity.SysMenu;
import org.litu.app.entity.SysRolemenu;
import org.litu.app.service.ISysMenuService;
import org.litu.base.service.impl.BaseTreeServiceImpl;
import org.litu.core.exception.LtParamException;
import org.litu.core.login.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单服务实现类 yueye 这个类中的方法还需要处理
 *
 * @author ltgk
 * @since 2018-10-23
 */
@Service
public class SysMenuServiceImpl extends BaseTreeServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    SysRolemenuMapper roleMenuMapper;

    @Override
    public void beforeTree(SysMenu entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysMenu> query) {
        query.eq(SysMenu::getSystemCode, entity.getSystemCode());

        super.beforeTree(entity, keyword, params, query);
    }

    /**
     * 在本系统进行系统类型选择时，选择当前系统。
     *
     * @param entity
     * @return
     */
    @Override
    public boolean beforeSave(UserInfo user, SysMenu entity, Map<String, String> params) {
        if (StringUtils.isBlank(entity.getSystemCode())) {
            entity.setSystemCode(SysContant.CURRENT_SYSTEM_CODE);
        }
        return super.beforeSave(user, entity, params);
    }

    /**
     * 删除的方法重写，添加校验内容。
     *
     * @param id
     * @param params
     * @param entity
     * @return
     */
    @Override
    public boolean beforeDelete(String id, Map<String, String> params, SysMenu entity) {
        boolean result = super.beforeDelete(id, params, entity);
        if (result) {
            LambdaQueryWrapper<SysRolemenu> rolemenuQueryWrapper = Wrappers.lambdaQuery();
            rolemenuQueryWrapper.eq(SysRolemenu::getMenuId, id);
            int rmNum = roleMenuMapper.selectCount(rolemenuQueryWrapper);
            if (rmNum > 0) {
                throw new LtParamException("当前菜单在角色中已经设置，请检查后再删除！");
            }
        }
        return result;
    }

    /**
     * 获取用户的菜单信息（权限） @param userId 用户ID @param menuTypes 权限的类型：
     * 1-模块，2-方法，3-按钮 @return @throws
     */
    @Override
    public List<SysMenu> userMenus(String userId, String systemCode, List<String> menuTypes) {
        List<SysMenu> sysMenus = baseMapper.userMenus(userId, systemCode, menuTypes);
        return sysMenus;
    }

    /**
     * 获取用户权限code
     *
     * @param userId
     * @return
     */
    public List<String> userPrivileges(String userId, String systemCode) {
        List<SysMenu> menus = baseMapper.userPrivileges(userId, systemCode);
        List<String> menuCodes = new ArrayList<String>();
        for (SysMenu menu : menus) {
            menuCodes.add(menu.getCode());
        }
        return menuCodes;
    }
}
