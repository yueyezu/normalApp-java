package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysRoleMapper;
import org.litu.app.dao.SysUserRoleMapper;
import org.litu.app.entity.system.SysRole;
import org.litu.app.entity.system.SysUserRole;
import org.litu.app.service.ISysUserRoleService;
import org.litu.app.entity.vo.UserRole;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据userId查找
     *
     * @return
     */
    public List<SysUserRole> getByUserId(String userId) {
        List<SysUserRole> result = new ArrayList<SysUserRole>();
        if (StringUtils.isBlank(userId)) {
            return result;
        }
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        return list(queryWrapper);
    }

    /**
     * 用户角色选项
     *
     * @param userId
     * @return
     */
    public List<UserRole> userRoles(String userId) {
        //获取未删除的role
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRole::getType, SysContant.ROLETYPE_ROLE);
        List<SysRole> roles = sysRoleMapper.selectList(queryWrapper);

        List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
        if (StringUtils.isNotBlank(userId)) {
            userRoles = getByUserId(userId);
        }

        List<UserRole> result = new ArrayList<UserRole>();
        out:
        for (SysRole role : roles) {
            UserRole userRole = new UserRole();
            userRole.init(role);
            result.add(userRole);
            for (SysUserRole ur : userRoles) {
                if (ur.getRoleId().equals(role.getId())) {
                    userRole.setChecked(true);
                    continue out;
                }
            }
        }

        return result;
    }

    /**
     * 更新角色权限
     *
     * @param userId
     * @param roleIds
     * @return
     */
    public boolean save(String userId, String... roleIds) {
        if (StringUtils.isBlank(userId) || (roleIds != null && roleIds.length > 0 && StringUtils.isAnyBlank(roleIds))) {
            return false;
        }
        removeByUserId(userId);
        List<SysUserRole> entityList = new ArrayList<SysUserRole>();
        for (String roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            entityList.add(userRole);
        }
        return super.saveBatch(entityList);
    }

    /**
     * 删除角色 的权限
     *
     * @param userId
     * @return
     */
    public boolean removeByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        return remove(queryWrapper);
    }

    /**
     * 根据权限id删除
     *
     * @param roleId
     * @return
     */
    public boolean removeByRole(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return false;
        }
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        return remove(queryWrapper);
    }
}
