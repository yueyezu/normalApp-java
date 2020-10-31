package org.litu.core.annotation;

import java.lang.annotation.*;

/**
 * 系统操作日志标注注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LtLog {

    /**
     * 当前的系统功能模块
     *
     * @return  当前系统功能模块
     */
    String module() default "";

    /**
     * 是否进行当前方法的日志记录
     *
     * @return true则表明进行当前方法日志记录
     */
    boolean enable() default true;
}
