package org.litu.core.config;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

    /**
     * shiro路径拦截信息的配置
     *
     * @return 访问每一个页面对应相应权限
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "anon"); // 登录界面
        chainDefinition.addPathDefinition("/api/**", "anon"); // API访问路径不需要加限制
        chainDefinition.addPathDefinition("/static/**", "anon"); // 静态资源不过滤
        chainDefinition.addPathDefinition("/error/*", "anon"); // 错误的界面
        chainDefinition.addPathDefinition("/public/**", "anon"); // public路径下的请求都不需要权限限制
        chainDefinition.addPathDefinition("/druid", "authc,roles[admin]");
        chainDefinition.addPathDefinition("/actuator/**", "authc,roles[admin]");
        chainDefinition.addPathDefinition("/**", "authc");

        return chainDefinition;
    }

    /* *
     * rememberMe管理器配置
     *
     * @return
     */
//    @Bean
//    public CookieRememberMeManager cookieRememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setMaxAge(259200000);
//        cookieRememberMeManager.setCookie(simpleCookie);
//        return cookieRememberMeManager;
//    }
}