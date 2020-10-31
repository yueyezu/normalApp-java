package org.litu.base.util;

import org.litu.base.config.BaseConstant;
import org.litu.core.login.ShiroSessionUtil;

public class UserUtil extends ShiroSessionUtil {

    /**
     * 获取当前登录用户的userId
     *
     * @return
     */
    public static String getUserId() {
        return session(BaseConstant.SESSION_CURRENT_USERID);
    }
}
