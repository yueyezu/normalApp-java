package org.litu.util.common;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 数据校验类 功能:提供一些对象有效性校验的方法
 * TODO 该验证组件需要再研究下使用方式，暂时先保留
 */
@SuppressWarnings("rawtypes")
public final class ValidateUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 如果校验成功，该集合为空；否则，该集合非空，集合中的每一个元素（ConstraintViolation类型）对应一个违反的约束
     *
     * @param t   泛型类实例
     * @param <T> 泛型
     * @return ConstraintViolation类型集合
     */
    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        return validator.validate(t);
    }

    /**
     * 判断字符串有效性
     */
    public static boolean valid(String src) {
        return !(src == null || "".equals(src.trim()));
    }

    /**
     * 判断一组字符串是否有效
     *
     * @param src 字符串组
     * @return true为字符串有效
     */
    public static boolean valid(String... src) {
        for (String s : src) {
            if (!valid(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个对象是否为空
     *
     * @param obj 需要判断的对象
     * @return true为对象不为空
     */
    public static boolean valid(Object obj) {
        return !(null == obj);
    }

    /**
     * 判断一组对象是否有效
     *
     * @param objs 需要判断的对象
     * @return true为对象不为空且长度不为0
     */
    public static boolean valid(Object... objs) {
        return objs != null && objs.length != 0;
    }

    /**
     * 判断集合的有效性
     *
     * @param col 需要判断的对象
     * @return true为集合不为空
     */
    public static boolean valid(Collection col) {
        return !(col == null || col.isEmpty());
    }

    /**
     * 判断一组集合是否有效
     *
     * @param cols 需要判断的集合
     * @return true为集合有效
     */
    public static boolean valid(Collection... cols) {
        for (Collection c : cols) {
            if (!valid(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断map是否有效
     *
     * @param map 需要判断的对象
     * @return true为map有效
     */
    public static boolean valid(Map map) {
        return !(map == null || map.isEmpty());
    }

    /**
     * 判断一组map是否有效
     *
     * @param maps 需要判断的对象
     *             需要判断map
     * @return 是否全部有效，true为全部有效
     */
    public static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }
        return true;
    }
}
