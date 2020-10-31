package org.litu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableCaching
public class Application extends SpringBootServletInitializer {

    /**
     * 当系统使用jar包方式生成，启动的方法
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Application.class);

        logger.info("---------------系统开始启动------------------");
        SpringApplication.run(Application.class, args);
        logger.info("---------------系统启动完成------------------");

        System.out.println("***************************************");
        System.out.println("*  _       _   _______   _      _     *");
        System.out.println("* | |     (_) |___ ___| | |    | |    *");
        System.out.println("* | |      _     | |    | |    | |    *");
        System.out.println("* | |     | |    | |    | |    | |    *");
        System.out.println("* | |___  | |    | |    | \\____/ |    *");
        System.out.println("* |_____| |_|    |_|     \\______/     *");
        System.out.println("* ~~ 跟我一起祈祷：别出bug！别出bug！别出bug！~~ *");
        System.out.println("***************************************");
    }

    /************************** 华丽的分割线：上报是jar包的方法，下边是war包的方法。 *****************************/

    /**
     * jar包时，项目启动的方法
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        SpringApplicationBuilder applicationBuilder = builder.sources(Application.class);

        LoggerFactory.getLogger(Application.class).info("---------------系统开始启动------------------");
        return applicationBuilder;
    }

    /**
     * jar包时，启动监控的方法
     *
     * @param servletContext
     * @return
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        LoggerFactory.getLogger(Application.class).info("---------------系统启动结束------------------");
    }
}
