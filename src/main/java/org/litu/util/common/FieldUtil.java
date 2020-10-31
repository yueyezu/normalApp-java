package org.litu.util.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射的工具类
 */
public class FieldUtil extends FieldUtils {
    public static Logger logger = LoggerFactory.getLogger(FieldUtils.class);

    /**
     * 获取对象中指定字段
     *
     * @param target    对象
     * @param fieldName 字段信息
     * @param <T>       返回值的类型
     * @return 返回对应的值
     */
    public static <T> T read(Object target, String fieldName) {
        try {
            if (target != null && !StringUtils.isBlank(fieldName)) {
                return (T) readField(target, fieldName, true);
            } else {
                return null;
            }
        } catch (IllegalAccessException e) {
            logger.error(String.format("在类[%s]中读取字段[%s]错误", target.toString(), fieldName), e);
            return null;
        }
    }

    /**
     * 为对象指定字段赋值
     *
     * @param target    对象
     * @param fieldName 字段名
     * @param value     值
     * @return 是否获取成功，如果获取成功返回true
     */
    public static boolean write(Object target, String fieldName, Object value) {
        if (target != null && !StringUtils.isBlank(fieldName)) {
            try {
                writeField(target, fieldName, value, true);
                return true;
            } catch (Exception e) {
                logger.error(String.format("对象[%s]字段写入字段[%s]值[%s]错误", target.toString(), fieldName, value), e);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检测字段是否存在属性
     *
     * @param type      对象的类型
     * @param fieldName 字段的名称
     * @return true表示字段存在值
     */
    public static boolean hasProperty(Class<?> type, String fieldName) {
        if (type != null && !StringUtils.isBlank(fieldName)) {
            try {
                return getField(type, fieldName, true) != null;
            } catch (Exception e) {
                logger.error(String.format("在类[%s]判断字段[%s]是否存在错误", type.toString(), fieldName), e);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检测字段是否存在属性
     *
     * @param target    对象实体
     * @param fieldName 对象字段名称
     * @return true表示字段存在值
     */
    public static boolean hasProperty(Object target, String fieldName) {
        return target != null && hasProperty(target.getClass(), fieldName);
    }
}