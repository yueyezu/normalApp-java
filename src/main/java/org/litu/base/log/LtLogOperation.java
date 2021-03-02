package org.litu.base.log;

import java.lang.annotation.*;

/**
 * 系统操作日志标注注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LtLogOperation {

    //TODO 对于操作类型之类的需要考虑作为枚举来最好

    /**
     * 当前的系统功能模块
     * 如果类和方法同时设置了该属性，则以方法的为准
     *
     * @return 当前系统功能模块
     */
    String module() default "";

    /**
     * 操作类型,枚举
     * 与operation同时存在时，operationEx为主。
     */
    LtLogOperationEnum operation() default LtLogOperationEnum.NONE;

    /**
     * 自己注明的操作类型
     * 与operation同时存在时，operationEx为主。
     *
     * @return
     */
    String operationEx() default "";


    /**
     * 是否进行当前方法的日志记录
     *
     * @return true则表明进行当前方法日志记录
     */
    boolean enable() default true;
}
