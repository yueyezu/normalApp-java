package org.litu.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5工具类
 * MD5（Message-Digest Algorithm 5），即消息摘要算法第五版，是一种被广泛使用的密码散列函数。可以产生出一个128位（16字节）的散列值
 * 功能:对文本数据的加密
 */
public class MD5Util {
    public static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    // 16进制的字符数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * @param source    原字符串
     * @param encoding  指定编码类型
     * @param uppercase 是否转为大写字符串
     */
    public static String MD5Encode_32(String source, boolean uppercase, String encoding) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            // result = parseByte2HexStr(messageDigest.digest());
            result = byteArrayToHexString(messageDigest.digest());
        } catch (Exception e) {
            logger.error("MD5码生成错误", e);
        }
        return uppercase ? result.toUpperCase() : result;
    }

    public static String MD5Encode_32(String source) {
        return MD5Encode_32(source, false, "UTF-8");
    }

    /// <summary>
    /// 获得16位的MD5加密
    /// </summary>
    public static String MD5Encode_16(String input) {
        return MD5Encode_32(input).substring(8, 24);
    }

    /// <summary>
    /// 获得8位的MD5加密
    /// </summary>
    public static String MD5Encode_8(String input) {
        return MD5Encode_32(input).substring(8, 16);
    }

    /// <summary>
    /// 获得4位的MD5加密
    /// </summary>
    public static String MD5Encode_4(String input) {
        return MD5Encode_32(input).substring(8, 12);
    }

    public static String MD5Encode_32(String source, boolean uppercase) {
        return MD5Encode_32(source, uppercase, "UTF-8");
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }

    // 16进制转byte[]
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        String str = MD5Encode_32("123", false, "UTF-8");
        System.out.println(str);
    }
}
