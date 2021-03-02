package org.litu.core.login;

import org.litu.core.exception.LtServerException;
import org.litu.util.Util;
import org.litu.util.security.DESUtil;
import org.litu.util.security.MD5Util;
import org.springframework.beans.factory.annotation.Value;

public class PasswordUtil {

    // 默认密码
    private static String defaultPwd = "111111";

    @Value("ltsystem.token.defPwd")
    public void setDefaultPwd(String defaultPwd) {
        PasswordUtil.defaultPwd = defaultPwd;
    }

    public static String getDefaultPwd() {
        return PasswordUtil.defaultPwd;
    }

    /**
     * 获取用户密码加密的密钥
     *
     * @return
     */
    public static String GetSecretkey() {
        return MD5Util.MD5Encode_16(Util.CreateNo());
    }

    /**
     * 获取用户的数据库存储的密码,默认密码
     *
     * @return
     */
    public static String GetDbPassword(String secretKey) {
        String password = MD5Util.MD5Encode_32(defaultPwd);
        String dbPassword = null;
        try {
            dbPassword = MD5Util.MD5Encode_32(DESUtil.encrypt(password.toLowerCase(), secretKey.toLowerCase()).toLowerCase());
        } catch (Exception e) {
            throw new LtServerException("密码生成错误！", e);
        }
        return dbPassword;
    }

    /// <summary>
    /// 获取用户的数据库存储的密码,密文
    /// 这里如果用户的密码不录入，则认为是默认密码
    /// </summary>
    /// <param name="secretKey">加密的密钥</param>
    /// <param name="password">密码明文,如果插入的为null，则使用默认密码</param>
    /// <returns>加密后的密码密文</returns>
    public static String GetDbPassword(String secretKey, String password) {
        String dbPassword = null;
        try {
            dbPassword = MD5Util.MD5Encode_32(DESUtil.encrypt(password.toLowerCase(), secretKey.toLowerCase()).toLowerCase());
        } catch (Exception e) {
            throw new LtServerException("密码生成错误！", e);
        }
        return dbPassword;
    }

    public static Boolean isDefaultPwd(String password) {
        String defPassword = MD5Util.MD5Encode_32(defaultPwd);
        return defPassword.equals(password);
    }
}
