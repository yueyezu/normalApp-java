package org.litu.util.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 加密解密工具类-DES
 * Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法,为密码体制中的对称密码体制。
 * 功能: DES加密,DES解密
 */

public class DESUtil {
    public final static String DESKey = "LT_desencrypt_2016";

    /**
     * Description 根据键值进行加密
     *
     * @param data 需要加密的数据
     * @return 加密后数据
     * @throws Exception 抛出异常
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(), MD5Util.MD5Encode_8(DESKey).getBytes());
        Encoder decoder = Base64.getEncoder();

        return decoder.encodeToString(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 需要加密的数据
     * @param key  加密键byte数组
     * @return 加密后数据
     * @throws Exception 抛出异常
     */
    public static String encrypt(String data, String key) throws Exception {
        String mdbKey = MD5Util.MD5Encode_8(key);
        byte[] bt = encrypt(data.getBytes(), mdbKey.getBytes());
        Encoder decoder = Base64.getEncoder();
        return decoder.encodeToString(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 需要加密的数据
     * @return 解密后数据
     * @throws IOException 抛出IO异常
     * @throws Exception   抛出异常
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
        Decoder decoder = Base64.getDecoder();
        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf, MD5Util.MD5Encode_8(DESKey).getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 需要解密数据
     * @param key  加密键byte数组
     * @return 解密后数据
     * @throws IOException 抛出IO异常
     * @throws Exception   抛出异常
     */
    public static String decrypt(String data, String key) throws IOException, Exception {
        if (data == null)
            return null;
        Decoder decoder = Base64.getDecoder();

        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf, MD5Util.MD5Encode_8(key).getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 需要加密数据
     * @param key  加密键byte数组
     * @return 加密后数据
     * @throws Exception 抛出异常
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom(key);     // 生成一个可信任的随机数源
        DESKeySpec dks = new DESKeySpec(key);        // 从原始密钥数据创建DESKeySpec对象

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DES");        // Cipher对象实际完成加密操作
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);       // 用密钥初始化Cipher对象

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 需要解密数据
     * @param key  键  加密键byte数组
     * @return 解密后数据
     * @throws Exception 抛出异常
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom(key);       // 生成一个可信任的随机数源

        DESKeySpec dks = new DESKeySpec(key);        // 从原始密钥数据创建DESKeySpec对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DES");   // Cipher对象实际完成解密操作
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);        // 用密钥初始化Cipher对象

        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String KEY = "wang!@#$%";

        String s1 = DESUtil.encrypt("123", KEY);
        String s2 = DESUtil.decrypt(s1, KEY);
        String s3 = DESUtil.encrypt("123", KEY);
        String s4 = DESUtil.decrypt(s3, KEY);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
    }
}

