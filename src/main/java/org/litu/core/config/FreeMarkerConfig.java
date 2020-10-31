package org.litu.core.config;

import org.litu.core.config.shiroFreemarkerTags.ShiroTags;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreeMarkerConfig implements InitializingBean {
    @Autowired
    private freemarker.template.Configuration configuration;

    /**
     * Spring 初始化的时候加载配置
     */
    @PostConstruct
    public void setConfigure() {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setTimeFormat("HH:mm:ss");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setURLEscapingCharset("UTF-8");
        configuration.setNumberFormat("0.##################");
        configuration.setBooleanFormat("true,false");
        configuration.setLocalizedLookup(false);
        configuration.setTemplateUpdateDelayMilliseconds(5);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加上这句后，可以在页面上使用shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
