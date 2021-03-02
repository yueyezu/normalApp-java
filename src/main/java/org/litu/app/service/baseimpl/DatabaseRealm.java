package org.litu.app.service.baseimpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.SysUser;
import org.litu.app.entity.SysUserlogin;
import org.litu.app.service.ISysMenuService;
import org.litu.app.service.ISysRoleService;
import org.litu.app.service.ISysUserService;
import org.litu.app.service.ISysUserloginService;
import org.litu.core.exception.LtServerException;
import org.litu.core.login.ShiroLoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * shiro realm配置
 */
@Component("authorizer")
public class DatabaseRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserloginService sysUserLoginService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private ISysRoleService sysRoleService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String account = ((SysUser) principals.getPrimaryPrincipal()).getAccount();
        if (StringUtils.isBlank(account)) {
            return authorizationInfo;
        }
        List<String> userRoles = ShiroLoginUtil.getCurrentRoles();
        List<String> userPermissions = ShiroLoginUtil.getCurrentPermission();

        if (userRoles == null || userPermissions == null || userRoles.isEmpty() || userPermissions.isEmpty()) {
            try {
                SysUser user = userService.getByAccount(account);
                // 用户角色
                userRoles = sysRoleService.userRoles(user.getId());
                // 用户权限
                userPermissions = sysMenuService.userPrivileges(user.getId(), SysContant.CURRENT_SYSTEM_CODE);
                ShiroLoginUtil.setLoginMsg(userRoles, userPermissions);
            } catch (LtServerException se) {
                log.error(se.getMessage(), se);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        // 为当前用户设置角色和权限
        authorizationInfo.addRoles(userRoles);
        authorizationInfo.addStringPermissions(userPermissions);

        return authorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 登录时，用UsernamePasswordToken对象用来存放提交的登录信息作为AuthenticationToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        if (StringUtils.isBlank(userName)) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        // 去用户表中验证用户名和密码
        try {
            // 获取用户名密码
            SysUser user = userService.getByAccount(userName);
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            SysUserlogin userLogin = sysUserLoginService.getByUserId(user.getId());
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, userLogin.getPassword(), getName());

        } catch (LtServerException se) {
            log.error(se.getMessage(), se);
            throw new AuthenticationException(se.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return simpleAuthenticationInfo;
    }
}