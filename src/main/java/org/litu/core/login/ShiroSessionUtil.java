package org.litu.core.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.litu.app.entity.SysUser;

import java.io.Serializable;
import java.util.List;

public class ShiroSessionUtil {
    // 当前登陆用户，存储的用户权限列表信息
    private static final String SESSION_CURRENT_PERMISSION = "userPermission";
    private static final String SESSION_CURRENT_ROLE = "userRole";

    /* *********************** 当前登陆需要使用的方法 ************************ */

    /**
     * 获取当前登录用户的user账号
     *
     * @return 当前用户账号
     */
    public static String getUserAccount() {
        PrincipalCollection object = session(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        return object.toString();
    }

    /**
     * 获取当前登陆用户
     *
     * @return SysUser
     */
    public static SysUser getCurrentUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

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
