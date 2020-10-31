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
        Serializable nowId = ShiroSessionUtil.getSessionId();
        for (Session session : sessions) {
            if (nowId != session.getId()) {
                PrincipalCollection object = ShiroSessionUtil.session(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
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
}
