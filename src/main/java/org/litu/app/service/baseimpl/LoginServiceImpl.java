package org.litu.app.service.baseimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.SysMenu;
import org.litu.app.entity.SysSystem;
import org.litu.app.entity.SysUser;
import org.litu.app.entity.SysUserlogin;
import org.litu.app.service.*;
import org.litu.app.vo.LoginUserMsg;
import org.litu.base.service.ILoginService;
import org.litu.base.util.UserUtil;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.exception.LtServerException;
import org.litu.core.login.LoginTokenUtil;
import org.litu.core.login.ShiroLoginUtil;
import org.litu.core.login.ShiroSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserloginService sysUserLoginService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private ISysSystemService SysSystemService;
    @Autowired
    private ShiroLoginUtil shiroLoginUtil;

    /**
     * 系统编号认证，用于判断当前系统编码是否被管理
     *
     * @param systemCode
     * @return
     */
    @Override
    public boolean checkSystemCode(String systemCode) {
        LambdaQueryWrapper<SysSystem> query = Wrappers.lambdaQuery();
        query.eq(SysSystem::getfCode, systemCode);
        SysSystem client = SysSystemService.getOne(query);

        return client != null;
    }

    /**
     * 根据用户的账号、密码信息，对账号信息进行校验，包括账号的用户名、密码等信息。
     *
     * @param systemCode 当前用户登录系统的编号
     * @param account    用户账号信息
     * @param password   账号密码信息
     * @return 如果登录成功返回用户信息，如果登录失败返回null， 或者抛出对应错误信息的异常
     * @author yueye
     */
    @Override
    public SysUser checkLogin(String systemCode, String account, String password) throws Exception, LtServerException {
        // 获取用户以及验证账号
        SysUser user = sysUserService.getByAccount(account);
        // 未找到用户
        if (user == null) {
            throw new LtServerException(ErrorEnum.UserPwdError);
        }
        // 用户被禁用
        if (user.getfDeleteflag().intValue() == SysContant.FLAG_TRUE) {
            throw new LtServerException(ErrorEnum.UserNotEnable);
        }
        // 验证密码
        String userId = user.getfId();
        SysUserlogin sysUserLogin = sysUserLoginService.getByUserId(userId);

        // 是否允许登录
        if (sysUserLogin.getfEnablelogin().intValue() == SysContant.FLAG_FALSE) {
            throw new LtServerException(ErrorEnum.UserNotEnable);
        }
        // 验证密码是否正确
        String nowPwd = LoginTokenUtil.GetDbPassword(sysUserLogin.getfSecretkey(), password);
        if (!nowPwd.equals(sysUserLogin.getfPassword())) {
            throw new LtServerException(ErrorEnum.UserPwdError);
        }
        // 是否有菜单,只看是否有功能权限即可
        List<String> menuTypes = new ArrayList<String>();
        menuTypes.add(SysContant.MENUTYPE_MODULE);
        menuTypes.add(SysContant.MENUTYPE_FUNCTION);
        List<SysMenu> menus = sysMenuService.userMenus(userId, systemCode, menuTypes);
        if (menus == null || menus.size() == 0) {
            throw new LtServerException(ErrorEnum.UserHasNoAuth);
        }

        return user;
    }

    /**
     * shario认证，对用户的登录信息进行校验，这里校验不包含用户的密码校验 这里就是shario的登录
     *
     * @param systemCode 当前用户登录系统的编号
     * @param account    用户账号信息
     * @param password   账号密码信息
     * @return
     * @author yueye
     */
    @Override
    public SysUser checkLoginShiro(String systemCode, String account, String password) {
        // 获取用户以及验证账号
        SysUser user = sysUserService.getByAccount(account);
        // 未找到用户
        if (user == null) {
            throw new LtServerException(ErrorEnum.UserPwdError);
        }
        // 用户被禁用
        if (user.getfDeleteflag() == SysContant.FLAG_TRUE) {
            throw new LtServerException(ErrorEnum.UserNotEnable);
        }
        // 验证密码
        SysUserlogin sysUserLogin = sysUserLoginService.getByUserId(user.getfId());
        // 是否允许登录
        if (sysUserLogin.getfEnablelogin().intValue() == SysContant.FLAG_FALSE) {
            throw new LtServerException(ErrorEnum.UserNotEnable);
        }
        // 是否有菜单,只看是否有功能权限即可
        List<String> menuTypes = new ArrayList<String>();
        menuTypes.add(SysContant.MENUTYPE_FUNCTION);
        List<SysMenu> menus = sysMenuService.userMenus(user.getfId(), systemCode, menuTypes);
        if (menus == null || menus.size() == 0) {
            throw new LtServerException(ErrorEnum.UserHasNoAuth);
        }

        // 是否多点登录
        boolean mulitiLogin = sysUserLogin.getfMultiuserlogin().intValue() == SysContant.FLAG_TRUE;
        try {
            // 登录
            String nowPwd = LoginTokenUtil.GetDbPassword(sysUserLogin.getfSecretkey(), password);
            shiroLoginUtil.login(account, nowPwd, !mulitiLogin);
        } catch (Exception e) {
            return null;
        }

        // 用户登陆验证成功后，对session操作,存储用户的信息
        ShiroSessionUtil.session(SysContant.SESSION_CURRENT_USERID, user.getfId()); // 用户ID
        Boolean isDefaultPwd = LoginTokenUtil.isDefaultPwd(password);
        ShiroSessionUtil.session(SysContant.SESSION_IS_DEFAULT_PWD, isDefaultPwd.toString());   // 用户是否默认密码
        List<String> userRoles = sysRoleService.userRoles(user.getfId());
        List<String> userPermissions = sysMenuService.userPrivileges(user.getfId(), SysContant.CURRENT_SYSTEM_CODE);
        UserUtil.setLoginMsg(userRoles, userPermissions); // 用户权限信息

        return user;
    }

    /**
     * 根据系统用户的信息，获取当前用户登录需要反馈的信息。
     *
     * @param userId     用户ID
     * @param systemCode 系统的Code
     * @return
     * @author yueye
     */
    @Override
    public LoginUserMsg getLoginUserMsg(String userId, String systemCode) {
        SysUser user = sysUserService.detail(userId);
        return getSsoUserMsg(user, systemCode);
    }

    /**
     * 根据系统用户的信息，获取当前用户登录需要反馈的信息。
     *
     * @param user 用户信息
     * @return
     * @author yueye
     */
    @Override
    public LoginUserMsg getSsoUserMsg(SysUser user, String systemCode) {
        // 获取用户的角色信息
        List<String> roles = sysRoleService.userRoles(user.getfId());
        // 返回用户信息以及菜单信息
        List<String> menuTypes = new ArrayList<String>();
        menuTypes.add(SysContant.MENUTYPE_MODULE);
        menuTypes.add(SysContant.MENUTYPE_FUNCTION);
        menuTypes.add(SysContant.MENUTYPE_BUTTON);
        List<SysMenu> menus = sysMenuService.userMenus(user.getfId(), systemCode, menuTypes);

        LoginUserMsg userMsg = new LoginUserMsg(user, menus, roles);

        return userMsg;
    }

    /**
     * 更新用户登录表中的登录状态信息
     *
     * @param userId
     * @param loginStatus
     * @return
     * @author yueye
     */
    @Override
    public boolean ChangeLoginStatus(String userId, Integer loginStatus) {
        SysUserlogin sysUserLogin = sysUserLoginService.getByUserId(userId);
        sysUserLogin.setfLoginstatus(loginStatus);
        if (loginStatus.equals(SysContant.FLAG_TRUE)) {
            sysUserLogin.setfLastvisittime(new Date());
            sysUserLogin.setfLogoncount(sysUserLogin.getfLogoncount() + 1);
        }

        sysUserLoginService.updateById(sysUserLogin);
        return false;
    }

    /**
     * shario认证，注销登录
     *
     * @return
     * @author yueye
     */
    @Override
    public boolean logoutShiro() {
        // 从session中移除userId
        ShiroSessionUtil.removeSession(SysContant.SESSION_CURRENT_USERID);
        ShiroSessionUtil.removeSession(SysContant.SESSION_IS_DEFAULT_PWD);
        ShiroSessionUtil.clearLoginMsg();
        boolean res = shiroLoginUtil.logout();

        return res;
    }

    /**
     * 根据用户账号，获取用户的基本信息
     *
     * @param account
     * @return
     */
    @Override
    public SysUser getUserByAccount(String account) {
        SysUser user = sysUserService.getByAccount(account);
        return user;
    }
}
