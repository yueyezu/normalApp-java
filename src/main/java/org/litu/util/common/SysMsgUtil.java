package org.litu.util.common;

import com.sun.management.OperatingSystemMXBean;
import org.litu.app.controller.IndexController;
import org.litu.util.configs.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 系统工具类 功能:提供些获取系统信息相关的工具方法
 */
public class SysMsgUtil {
    public static Logger logger = LoggerFactory.getLogger(SysMsgUtil.class);

    /**
     * JVM的版本
     */
    public static final String JVM_VERSION = PropertiesUtil.key("java.version");
    /**
     * JVM的编码
     */
    public static final String JVM_ENCODING = PropertiesUtil.key("file.encoding");
    /**
     * JVM默认的临时目录
     */
    public static final String JVM_TEMPDIR = PropertiesUtil.key("java.io.tmpdir");

    public static final String HTTP_PROXY_HOST = "http.proxyHost";
    public static final String HTTP_PROXY_PORT = "http.proxyPort";
    public static final String HTTP_PROXY_USER = "http.proxyUser";
    public static final String HTTP_PROXY_PASSWORD = "http.proxyPassword";
    /**
     * 主机IP
     */
    public static String HOST_IP;
    /**
     * 主机名
     */
    public static String HOST_NAME;

    /**
     * 主机架构
     */
    public static String OS_ARCH = PropertiesUtil.key("os.arch");
    /**
     * 主机类型
     */
    public static String OS_NAME = PropertiesUtil.key("os.name");
    /**
     * 主机类型版本
     */
    public static String OS_VERSION = PropertiesUtil.key("os.version");
    /**
     * 操作系统类型
     */
    public static String SUN_DESKTOP = PropertiesUtil.key("sun.desktop");
    /**
     * 当前用户
     */
    public static String CURRENT_USER = PropertiesUtil.key("user.name");
    /**
     * 当前用户的家目录
     */
    public static String CURRENT_USER_HOME = PropertiesUtil.key("user.home");

    /**
     * 总的物理内存
     */
    public static long TotalMemorySize;
    private static OperatingSystemMXBean osmxb;
    private static int kb = 1024;

    /**
     * 当用用户的工作目录
     */
    public static String CURRENT_USER_DIR = PropertiesUtil.key("user.dir");
    public static String FILE_SEPARATOR = PropertiesUtil.key("file.separator");
    public static String PATH_SEPARATOR = PropertiesUtil.key("path.separator");
    public static String LINE_SEPARATOR = PropertiesUtil.key("line.separator");

    static {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            HOST_NAME = addr.getHostName();
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                if (null != netint.getHardwareAddress()) {
                    List<InterfaceAddress> list = netint.getInterfaceAddresses();
                    for (InterfaceAddress interfaceAddress : list) {
                        InetAddress ip = interfaceAddress.getAddress();
                        if (ip instanceof Inet4Address) {
                            HOST_IP += interfaceAddress.getAddress().toString();
                        }
                    }
                }
            }
            HOST_IP = HOST_IP.replaceAll("null", "");
        } catch (Exception e) {
            System.out.println("获取服务器IP出错");
        }
        try {
            osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            TotalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        } catch (Exception e) {
            logger.error("获取系统信息失败", e);
        }
    }

    /**
     * 已使用的物理内存
     */
    public static long usedMemory() {
        if (ValidateUtil.valid(osmxb)) {
            return (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        }
        return 0;
    }

    /**
     * 获取JVM内存总量
     */
    public static long JVMtotalMem() {
        return Runtime.getRuntime().totalMemory() / kb;
    }

    /**
     * 虚拟机空闲内存量
     */
    public static long JVMfreeMem() {
        return Runtime.getRuntime().freeMemory() / kb;
    }

    /**
     * 虚拟机使用最大内存量
     */
    public static long JVMmaxMem() {
        return Runtime.getRuntime().maxMemory() / kb;
    }

    /**
     * Sets HTTP proxy settings.
     */
    public static void setHttpProxy(String host, String port, String username, String password) {
        System.getProperties().put(HTTP_PROXY_HOST, host);
        System.getProperties().put(HTTP_PROXY_PORT, port);
        System.getProperties().put(HTTP_PROXY_USER, username);
        System.getProperties().put(HTTP_PROXY_PASSWORD, password);
    }

    /**
     * Sets HTTP proxy settings.
     */
    public static void setHttpProxy(String host, String port) {
        System.getProperties().put(HTTP_PROXY_HOST, host);
        System.getProperties().put(HTTP_PROXY_PORT, port);
    }

    /**
     * 当前运行jar包的工作目录
     * 结果为：D:\编码实现\HaierResearch
     */
    public static String JarPath() {
        ApplicationHome home = new ApplicationHome(IndexController.class);
        String homePath = home.getDir().toString();
        homePath = homePath.replace("\\", "/");
        return homePath;
    }


    // 测试
    public static void main(String[] args) {
        // System.out.println(SysUtil.HOST_IP);
        // System.out.println(SysUtil.HOST_NAME);
        // System.out.println(SysUtil.OS_NAME);
        // System.out.println(SysUtil.OS_VERSION);
        // System.out.println(SysUtil.CURRENT_USER);
        // System.out.println(SysUtil.CURRENT_USER_HOME);
        // System.out.println(SysUtil.FILE_SEPARATOR);
        // System.out.println(SysUtil.PATH_SEPARATOR);
        // System.out.println(SysUtil.LINE_SEPARATOR);
        // System.out.println(SysUtil.TotalMemorySize);
        // System.out.println(SysUtil.JVM_VERSION);
        // System.out.println(SysUtil.JVM_TEMPDIR);
        // System.out.println(SysUtil.OS_ARCH);
        // System.out.println(SysUtil.SUN_DESKTOP);
        // System.out.println(SysUtil.usedMemory());
        // System.out.println(SysUtil.JVMtotalMem());
        // System.out.println(SysUtil.JVMfreeMem());
        // System.out.println(SysUtil.JVMmaxMem());
    }
}
