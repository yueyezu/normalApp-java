package org.litu.util.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session工具类
 * 功能:获取session对象,获取seesion中保存的值,设置session的值,修改session的值,删除session的值,获取request的对象
 **/
public class SessionUtil {
    /**
     * 获取request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 获取session
     *
     * @return session
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession(false);
    }

    /**
     * 获取session中的Attribute
     *
     * @param name 名字
     * @return session中的Attribute
     */
    public static Object getSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession().getAttribute(name);
    }

    /**
     * 设置session的Attribute
     *
     * @param name  名字
     * @param value 值
     */
    public static void setSessionAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 删除session中的Attribute
     *
     * @param name 根据名字删除
     */
    public static void removeSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().removeAttribute(name);
        }
    }

    /**
     * 设置session失效的时间
     *
     * @param time 最大有效时间，秒
     */
    public static void setSessionInvalid(int time) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setMaxInactiveInterval(time);
        }
    }
}