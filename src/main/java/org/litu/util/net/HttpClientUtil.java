package org.litu.util.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 远程连接，存放数据
 */
public class HttpClientUtil {
    public static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            URL url = new URL(httpurl);            // 创建远程url连接对象
            connection = (HttpURLConnection) url.openConnection();            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection.setRequestMethod("GET");            // 设置连接方式：get
            connection.setConnectTimeout(15000);            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setReadTimeout(60000);            // 设置读取远程返回的数据时间：60000毫秒
            connection.connect();            // 发送请求

            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));                // 封装输入流is，并指定字符集
                StringBuffer sbf = new StringBuffer();                // 存放数据
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            logger.error("进行Get请求出现错误", e);
        } finally {            // 关闭资源
            try {
                if (null != br) br.close();
                if (null != is) is.close();
            } catch (IOException e) {
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

    /**
     * 远程连接，读取数据
     *
     * @param httpUrl 远程地址
     * @param param   参数
     * @return 读取到的数据
     */
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();            // 通过远程url连接对象打开连接
            connection.setRequestMethod("POST");            // 设置连接请求方式
            connection.setConnectTimeout(15000);            // 设置连接主机服务器超时时间：15000毫秒
            connection.setReadTimeout(60000);            // 设置读取主机服务器返回数据超时时间：60000毫秒

            connection.setDoOutput(true);            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoInput(true);            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0

            os = connection.getOutputStream();            // 通过连接对象获取一个输出流
            os.write(param.getBytes());            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            if (connection.getResponseCode() == 200) {            // 通过连接对象获取一个输入流，向远程读取
                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            logger.error("进行POST请求出现错误", e);
        } finally { // 关闭资源
            try {
                if (null != br) br.close();
                if (null != os) os.close();
                if (null != is) is.close();
            } catch (IOException e) {
            }

            connection.disconnect();            // 断开与远程地址url的连接
        }
        return result;
    }
}