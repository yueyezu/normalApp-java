package org.litu.base.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 *
 * @author Administrator
 */
public class ThreadUtil {
    public static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    public static final ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(20);
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 判断key是否存在
     *
     * @param key 输入key
     * @return true为存在key
     */
    public static boolean contains(String key) {
        Map<String, Object> container = new HashMap<>();

        Map<String, Object> map = ObjectUtils.defaultIfNull(threadLocal.get(), container);
        return map.containsKey(key);
    }

    /**
     * 添加map
     *
     * @param key   key值
     * @param value value值
     */
    public static void put(String key, Object value) {
        Map<String, Object> container = new HashMap<>();
        Map<String, Object> map = ObjectUtils.defaultIfNull(threadLocal.get(), container);
        threadLocal.set(map);
        map.put(key, value);
    }


    /**
     * 通过key寻找map
     *
     * @param key 输入key
     * @param <T> 泛型
     * @return 通过key寻找map
     */
    public static <T> T get(String key) {
        Map<String, Object> container = new HashMap<>();
        Map<String, Object> map = ObjectUtils.defaultIfNull(threadLocal.get(), container);
        return (T) map.get(key);
    }

    /**
     * @param key   key值
     * @param value value值
     * @param <T>   泛型
     * @return 如果有key则通过key查找，否则通过value查找
     */
    public static <T> T get(String key, Object value) {
        Map<String, Object> container = new HashMap<>();
        Map<String, Object> map = ObjectUtils.defaultIfNull(threadLocal.get(), container);
        threadLocal.set(map);
        Object result = MapUtils.getObject(map, key, value);
        map.put(key, result);
        return (T) result;
    }

    /**
     * @param key key值
     * @param <T> 泛型
     * @return 进入移出方法
     */
    public static <T> T remove(String key) {
        Map<String, Object> container = new HashMap<>();
        Map<String, Object> map = ObjectUtils.defaultIfNull(threadLocal.get(), container);
        return (T) map.remove(key);
    }

    /**
     * map清除
     */
    public static void clear() {
        threadLocal.remove();
    }
}