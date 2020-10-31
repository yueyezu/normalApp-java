package org.litu.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密解密工具类
 * 高级加密标准(AES,Advanced Encryption Standard)为最常见的对称加密算法
 * 功能:AES加密,AES解密
 */
public class AESUtil {
    public static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 16进制的字符数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * AES 加密
     *
     * @param content 需要加密的内容
     * @param aesKey  加密密钥
     * @return 加密后密码
     */
    public static byte[] AESEncrypt(String content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(aesKey.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            return cipher.doFinal(byteContent); // 加密
        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException e) {
            logger.error("AES加密错误", e);
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param content 待解密内容
     * @param aesKey  解密密钥 秘
     * @return 解密后密码
     */
    public static byte[] AESDecrypt(byte[] content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(aesKey.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化

            return cipher.doFinal(content); // 加密
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error("AES解密错误", e);
        }
        return null;
    }

    /**
     * 将二进制转化为16进制
     *
     * @param buf byte数组
     * @return 参数转为16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转化为2进制
     *
     * @param hexStr 字符串
     * @return 参数转为2进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转化为字符串
     *
     * @param bytes byte数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制转byte[]
     *
     * @param b byte
     * @return 字符数组
     */

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 主函数
     *
     * @param args args参数
     */
    // 测试
    public static void main(String[] args) {
        String content = "123";
        String aesKey = "aesKey";
        // aes加密
        byte[] buf = AESUtil.AESEncrypt(content, aesKey);
        String str = AESUtil.byteArrayToHexString(buf);
        System.out.println(str);
        // aes解密
        byte[] b = AESUtil.parseHexStr2Byte(str);
        byte[] bbb = AESUtil.AESDecrypt(b, aesKey);
        String sss = new String(bbb);
        System.out.println(sss);
    }
}
