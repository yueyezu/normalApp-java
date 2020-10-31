package org.litu.util.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 序列化工具类
 * 功能:对对象的序列与反序列功能
 */
public class SerializeUtil {
    public static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * @param serStr 需要反序列化的字符串
     * @return Object 返回类型
     */
    public static Object getObjFromStr(String serStr) throws UnsupportedEncodingException {
        Object obj = null;

        String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("ISO-8859-1".getBytes(redStr));
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            logger.error("序列化对象错误", e);
        }
        return obj;
    }

    /**
     * @param obj 要序列化成字符串的对象
     * @return String 返回类型
     */
    public static String getStrFromObj(Serializable obj) {

        String serialStr = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        ) {
            objectOutputStream.writeObject(obj);
            serialStr = byteArrayOutputStream.toString("ISO-8859-1");
            serialStr = java.net.URLEncoder.encode(serialStr, "UTF-8");
        } catch (IOException e) {
            logger.error("反序列化对象错误", e);
        }

        return serialStr;
    }

    /**
     * 主函数
     *
     * @param args args参数
     * @throws UnsupportedEncodingException 转码异常
     * @throws IOException                  IO异常
     */
    public static void main(String[] args) throws IOException {
        String str = SerializeUtil.getStrFromObj("ni");
        Object obj = SerializeUtil.getObjFromStr(str);
        System.out.println(str);
        System.out.println(obj);
    }
}
