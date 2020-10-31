package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.litu.app.dao.SysRoleMapper;
import org.litu.app.dao.SysRolemenuMapper;
import org.litu.app.dao.SysUserroleMapper;
import org.litu.app.entity.SysRole;
import org.litu.app.entity.SysRolemenu;
import org.litu.app.entity.SysUserrole;
import org.litu.app.service.ISysRoleService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.exception.LtParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色/岗位服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    SysUserroleMapper userRoleMapper;
    @Autowired
    SysRolemenuMapper roleMenuMapper;

    @Override
    public void beforeList(SysRole entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysRole> query) {
        query.eq(SysRole::getfType, entity.getfType());
        query.and(i -> i.like(SysRole::getfCode, keyword).or().like(SysRole::getfName, keyword));
        query.orderByAsc(SysRole::getfSortnum);
    }

    @Override
    public void beforePage(SysRole entity, String keyword, IPage<SysRole> page, Map<String, String> params, LambdaQueryWrapper<SysRole> query) {
        query.eq(SysRole::getfType, entity.getfType());
        query.and(i -> i.like(SysRole::getfCode, keyword).or().like(SysRole::getfName, keyword));
        query.orderByAsc(SysRole::getfSortnum);
    }


    @Override
    public boolean beforeDelete(String id, Map<String, String> params, SysRole entity) {
        boolean result = super.beforeDelete(id, params, entity);

        if (result) {
            // 校验没有使用中
            LambdaQueryWrapper<SysUserrole> userroleQueryWrapper = Wrappers.lambdaQuery();
            userroleQueryWrapper.eq(SysUserrole::getfRoleid, id);
            int urNum = userRoleMapper.selectCount(userroleQueryWrapper);
            if (urNum > 0) {
                throw new LtParamException("当前角色信息使用中，不允许删除!");
            }

            // 删除该角色对应的菜单关联信息
            LambdaQueryWrapper<SysRolemenu> rolemenuQueryWrapper = Wrappers.lambdaQuery();
            rolemenuQueryWrapper.eq(SysRolemenu::getfRoleid, id);
            result = roleMenuMapper.delete(rolemenuQueryWrapper) >= 0;
        }

        return result;
    }

    /**
     * 用户角色fcode
     *
     * @param userId
     * @return
     */
    public List<String> userRoles(String userId) {
        List<String> roleCodes = new ArrayList<String>();
        List<SysRole> roles = baseMapper.userRoles(userId);
        for (SysRole role : roles) {
            roleCodes.add(role.getfCode());
        }
        return roleCodes;
    }
}
