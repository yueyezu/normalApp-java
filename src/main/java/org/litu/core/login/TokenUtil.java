package org.litu.core.login;

import org.apache.commons.lang3.StringUtils;
import org.litu.util.Util;
import org.litu.util.cache.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * redis操作工具对象
     */
    private final RedisUtil redisUtil;


    /**
     * token有效时间
     */
    @Value("${ltsystem.token.duration}")
    private Long duration;

    @Value("${ltsystem.token.tokenKey}")
    private String tokenKey;

    @Autowired
    public TokenUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisUtil = RedisUtil.Instance(redisTemplate);
    }

    /**
     * 返回前后端对接使用的tokenKey信息，所有相关系统需要统一,所以直接放在配置中
     *
     * @return
     */
    public String getTokenKey() {
        return tokenKey;
    }

    /*============= token生成与验证操作 ===============*/

    // TODO 第三方的token，后续将该方法调整的token复杂度高些。

    /**
     * 生成登陆用token
     *
     * @param cIp     客户端Ip
     * @param sysCode 登陆系统的Code
     * @return
     */
    public String newToken(String cIp, String sysCode) {
        String guid = Util.GuId32();
        return sysCode + guid;
    }

    /**
     * 校验token是否一致
     *
     * @param token    token
     * @param nowToken 现在的token
     * @return 若为true则token一致
     */
    public boolean isEqual(String token, String nowToken) {
        return token.equals(nowToken);
    }

    /*============= token存储相关操作——redis ===============*/

    /**
     * 提取token信息
     *
     * @param token
     * @return
     */
    public UserInfo getUser(String token) {
        if (StringUtils.isNotBlank(token) && redisUtil.hasKey(token)) {
            redisUtil.expire(token, duration);
            try {
                Object tokenObj = redisUtil.get(token);
                if (tokenObj == null) {
                    return null;
                }

                return (UserInfo) tokenObj;
            } catch (Exception ex) {
                logger.error("获取token信息，解析错误。", ex);
                return null;
            }
        }
        return null;
    }

    // 提取token信息
    public Object extractToken(String token) {
        if (StringUtils.isNotBlank(token) && redisUtil.hasKey(token)) {
            redisUtil.expire(token, duration);
            return redisUtil.get(token);
        }
        return null;
    }

    public void setToken(String token, UserInfo userInfo) {
        redisUtil.set(token, userInfo, duration);
    }

    public boolean hasToken(String token) {
        return redisUtil.hasKey(token);
    }

    public boolean delToken(String token) {
        redisUtil.del(token);
        return true;
    }
}
