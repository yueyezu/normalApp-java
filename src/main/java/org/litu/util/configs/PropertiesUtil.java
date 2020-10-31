package org.litu.util.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取properties的工具类 功能:读写properties文件的操作
 */
public class PropertiesUtil {
    public static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public static String key(String key) {
        return System.getProperty(key);
    }

    /**
     * 根据Key读取Value
     *
     * @param filePath 属性文件
     * @param key      需要读取的属性
     */
    public static String GetValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath));) {
            pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            logger.error("读取配置文件错误", e);
            return null;
        }
    }

    /**
     * 加载properties信息
     *
     * @param in 输入流
     * @return 全部properties信息
     */
    public static Map<String, String> properties(InputStream in) {
        Map<String, String> map = new HashMap<>();
        Properties pps = new Properties();
        try {
            pps.load(in);
        } catch (IOException e) {
            logger.error("load properties error:" + e.getMessage());
        }
        @SuppressWarnings("rawtypes")
        Enumeration en = pps.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            map.put(strKey, strValue);
        }
        return map;
    }

    /**
     * 读取Properties的全部信息
     *
     * @param filePath 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public static Map<String, String> GetAllProperties(String filePath) throws IOException {
        Map<String, String> map = new HashMap<>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            return properties(in);
        } catch (Exception e) {
            return map;
        }
    }

    /**
     * 写入Properties信息
     *
     * @param filePath 写入的属性文件
     * @param pKey     属性名称
     * @param pValue   属性值
     */
    public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
        Properties props = new Properties();

        props.load(new FileInputStream(filePath));
        // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream fos = new FileOutputStream(filePath);
        props.setProperty(pKey, pValue);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        props.store(fos, "Update '" + pKey + "' value");

    }

    /**
     * 主函数
     *
     * @param args args参数
     */
    public static void main(String[] args) {
        // Map<String,String>
        // map=GetAllProperties("C:\\Users\\Administrator\\eclipse-workspace\\LT.Util\\src\\main\\java\\log4j.properties");
        // System.out.println(map);
        // WriteProperties("C:\\Users\\Administrator\\eclipse-workspace\\LT.Util\\src\\main\\java\\log4j.properties",
        // "db.max", "20");
        // String
        // str=GetValueByKey("C:\\Users\\Administrator\\eclipse-workspace\\LT.Util\\src\\main\\java\\log4j.properties",
        // "log4j.rootLogger");
        // System.out.println(str);
    }
}
