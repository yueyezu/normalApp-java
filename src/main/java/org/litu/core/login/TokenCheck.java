package org.litu.core.login;

import java.lang.annotation.*;


/**
 * 用来跳过验证的PassToken
 * 默认所有为需要认证，然后设置PassToken则不去校验token信息
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TokenCheck {
    /**
     * 是否进行token的检验，默认需要检查token。
     *
     * @return
     */
    boolean check() default true;

    /**
     * 是否需要解析为用户，默认不需要进行转化
     *
     * @return
     */
    boolean toUser() default false;
}
