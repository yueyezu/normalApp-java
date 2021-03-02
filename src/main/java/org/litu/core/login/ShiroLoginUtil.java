package org.litu.core.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户登录/登出
 */
@Component
public class ShiroLoginUtil {

    @Autowired
    public SessionDAO sessionDAO;

    /**
     * 单点登录
     */
    private void logoutOtherUser(String userName) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Serializable nowId = getSessionId();
        for (Session session : sessions) {
            if (nowId != session.getId()) {
                PrincipalCollection object = session(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                String nowAccount = object.toString();
                if (userName.equals(nowAccount)) {
                    session.setTimeout(0);// 设置session立即失效，即将其踢出系统
                }
            }
        }
    }

    /**
     * shiro登录
     *
     * @param userName    用户名
     * @param password    加密过的密码
     * @param singleLogin 是否单点登录
     * @return 如果为true则登陆成功，但目前没有false
     */
    public boolean login(String userName, String password, boolean singleLogin) {
        boolean result = true;
        // 当前Subject
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        if (!currentUser.isAuthenticated()) {
            token.setRememberMe(false);
            currentUser.login(token);
            if (singleLogin) {
                logoutOtherUser(userName);// 单点登录
            }
        } else {
            String userName_online = (String) currentUser.getPrincipal();
            if (!userName.equals(userName_online)) {
                currentUser.logout();
                token.setRememberMe(false);// 开启rememberMe
                currentUser.login(token);
                if (singleLogin) {
                    logoutOtherUser(userName);// 单点登录
                }
            }
        }

        return result;
    }

    /**
     * shiro 退出
     *
     * @return true则为退出成功
     */
    public boolean logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /* *********************** 以下为静态方法，主要是Shiro的Session操作方法 ************************ */

    // 当前登陆用户，存储的用户权限列表信息
    private static final String SESSION_CURRENT_PERMISSION = "userPermission";
    private static final String SESSION_CURRENT_ROLE = "userRole";


    /**
     * 设置当前用户的登陆信息
     */
    public static void setLoginMsg(List<String> roles, List<String> permissions) {
        session(SESSION_CURRENT_ROLE, roles);
        session(SESSION_CURRENT_PERMISSION, permissions);
    }

    /**
     * 获取当前用户的角色信息
     *
     * @return 当前角色列表
     */
    public static List<String> getCurrentRoles() {
        return session(SESSION_CURRENT_ROLE);
    }

    /**
     * 获取当前用户的权限信息
     *
     * @return 权限名称列表
     */
    public static List<String> getCurrentPermission() {
        return session(SESSION_CURRENT_PERMISSION);
    }

    /**
     * 将用户当前信息在缓存中清空掉
     */
    public static void clearLoginMsg() {
        removeSession(SESSION_CURRENT_ROLE);
        removeSession(SESSION_CURRENT_PERMISSION);
    }

    /* *********************** 当前用户session的对象 *********************** */

    /**
     * 获取session对象
     *
     * @return 获取到的会话
     */
    private static Session session() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取 session中的属性
     *
     * @param attr 传入的属性
     * @return 会话中属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T session(String attr) {
        return (T) (session().getAttribute(attr));
    }

    /**
     * 设置 session中的属性
     */
    public static void session(String name, Object value) {
        session().setAttribute(name, value);
    }

    /**
     * 移除session的值
     *
     * @param name 通过属性中的名字移除session
     */
    public static void removeSession(String name) {
        session().removeAttribute(name);
    }

    /**
     * 获取当前session的ID信息
     *
     * @return 获取当前session的id
     */
    public static Serializable getSessionId() {
        return session().getId();
    }
}
