package org.litu.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地进程内缓存工具类 功能:将对象存放于缓存当中,获取缓存中的对象,清理缓存中的对象
 */
public class CacheUtil {

    private CacheUtil() {
    }

    public static CacheUtil getInstance() {
        return new CacheUtil();
    }

    // 缓存map
    private static Map<String, CacheEntity> cacheMap = new ConcurrentHashMap<>();

    // 启动一个线程,定时扫描map,清除过期数据
    public static Thread cleanMapThreadd = new Thread(new CleanMapTask());

    /**
     * 缓存类
     */
    private static class CacheEntity {
        private long expireTime;
        private Object o;

        public CacheEntity(Object o, long expireTime) {
            this.o = o;
            this.expireTime = expireTime;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public Object getO() {
            return o;
        }
    }

    /**
     * 清理缓存任务
     *
     * @author Administrator
     */
    private static class CleanMapTask implements Runnable {
        public void run() {
            while (true) {
                if (cacheMap.size() <= 0) {
                    cleanMapThreadd.interrupt();
                }
                try {
                    long currentTime = System.currentTimeMillis();
                    for (Map.Entry<String, CacheEntity> entry : cacheMap.entrySet()) {
                        CacheEntity entity = entry.getValue();
                        long expireTime = entity.getExpireTime();
                        if (currentTime > expireTime) {
                            cacheMap.remove(entry.getKey());
                        }
                    }
                    Thread.sleep(10 * 60 * 1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
     * 放入缓存
     *
     * @param key        缓存键
     * @param value      缓存对象
     * @param expireTime 缓存时间 单位秒
     */
    public static void put(String key, Object value, long expireTime) {
        cacheMap.put(key, new CacheEntity(value, System.currentTimeMillis() + expireTime * 1000));
        if (!cleanMapThreadd.isAlive()) {
            cleanMapThreadd.start();
        }
    }

    /**
     * 获取缓存对象
     *
     * @param key 缓存键
     * @return 缓存对象
     */
    public static Object get(String key) {
        CacheEntity entity = cacheMap.get(key);
        if (entity != null) {
            long currentTime = System.currentTimeMillis();
            long expireTime = entity.getExpireTime();
            if (currentTime <= expireTime) {
                return entity.getO();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}