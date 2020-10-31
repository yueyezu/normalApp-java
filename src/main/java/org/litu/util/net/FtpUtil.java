package org.litu.util.net;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * FTP工具类
 * 功能:对Ftp的一些操作
 */
public class FtpUtil {
    public static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private FTPClient ftpClient = null;
    private String server;
    private int port;
    private String userName;
    private String userPassword;

    /**
     * @param server       服务器
     * @param port         端口号
     * @param userName     FTP用户名
     * @param userPassword FTP密码
     */
    public FtpUtil(String server, int port, String userName, String userPassword) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * 连接服务器
     *
     * @return 连接成功与否 true:成功， false:失败
     */
    public boolean open() {
        if (ftpClient != null && ftpClient.isConnected()) {
            return true;
        }
        try {
            ftpClient = new FTPClient();
            // 连接
            ftpClient.connect(this.server, this.port);
            ftpClient.login(this.userName, this.userPassword);
            setFtpClient(ftpClient);
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                logger.error("FTP server refused connection.");
            }
            logger.info(String.format("open FTP success:%s:%s,uname=%s,pwd=%s"),
                    this.server, this.port, this.userName, this.userPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置上传模式.binally or ascii
            return true;
        } catch (Exception e) {
            this.close();
            logger.error(String.format("open FTP Error:%s:%s,uname=%s,pwd=%s"),
                    this.server, this.port, this.userName, this.userPassword);
            return false;
        }
    }

    /**
     * 获取目录下所有的文件名称
     *
     * @param filePath 指定的目录
     * @return 文件列表, 或者null
     */
    private FTPFile[] getFileList(String filePath) {
        try {
            return ftpClient.listFiles(filePath);
        } catch (IOException e) {
            logger.error("list files Error", e);
            return null;
        }
    }

    /**
     * 层层切换工作目录
     *
     * @param ftpPath 目的目录
     * @return 切换结果
     */
    public boolean changeDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {   // 只有一层目录
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), StandardCharsets.ISO_8859_1));
            } else {                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), StandardCharsets.ISO_8859_1));
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("change directory error", e);
            return false;
        }
    }

    /**
     * 切换到父目录
     *
     * @return 切换结果 true：成功， false：失败
     */
    public boolean changeToParentDir() {
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            logger.error("change to Parent dir error", e);
            return false;
        }
    }

    /**
     * 改变当前目录到指定目录
     *
     * @param dir 目的目录
     * @return 切换结果 true：成功，false：失败
     */
    public boolean cd(String dir) {
        try {
            return ftpClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
            logger.error("change current dir error", e);
            return false;
        }
    }

    /**
     * 循环创建目录，并且创建完目录后，设置工作目录为当前创建的目录下
     *
     * @param ftpPath 需要创建的目录
     * @return true为创建成功
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                ftpClient.makeDirectory(new String(ftpPath.getBytes(), StandardCharsets.ISO_8859_1));
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), StandardCharsets.ISO_8859_1));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(new String(paths[i].getBytes(), StandardCharsets.ISO_8859_1));
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), StandardCharsets.ISO_8859_1));
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("make dir error", e);
            return false;
        }
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param localDirectoryAndFileName 本地文件目录和文件名
     * @param ftpFileName               上传到服务器的文件名
     * @param ftpDirectory              FTP目录如:/path1/pathb2/,如果目录不存在会自动创建目录
     * @return true为上传成功
     */
    public boolean upload(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        if (ftpClient != null) {
            File srcFile = new File(localDirectoryAndFileName);

            try (FileInputStream fis = new FileInputStream(srcFile);) {
                this.mkDir(ftpDirectory);      // 创建目录
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);                // 设置文件类型（二进制）
                // 上传
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
            } catch (Exception e) {
                this.close();
                logger.error(String.format("FTP upload file：%s -> %s error", ftpDirectory, ftpDirectory), e);
                return false;
            }
        }
        logger.info(String.format("FTP upload file：%s -> %s success", ftpDirectory, ftpDirectory));

        return flag;
    }

    /**
     * 从FTP服务器上下载文件
     *
     * @param ftpDirectoryAndFileName   ftp服务器文件路径，以/dir形式开始
     * @param localDirectoryAndFileName 保存到本地的目录
     * @return true为下载成功
     */
    public boolean get(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        ftpClient.enterLocalPassiveMode(); // Use passive mode as default
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            this.changeDir(filePath);
            ftpClient.retrieveFile(new String(fileName.getBytes(), StandardCharsets.ISO_8859_1), new FileOutputStream(localDirectoryAndFileName)); // download
            // file
            logger.info(ftpClient.getReplyString()); // check result
            logger.info(String.format("ftp download file：%s -> %s", ftpDirectoryAndFileName, localDirectoryAndFileName));
            return true;
        } catch (IOException e) {
            logger.error(String.format("ftp download file：%s -> %s error", ftpDirectoryAndFileName, localDirectoryAndFileName), e);
            return false;
        }
    }

    /**
     * 返回FTP目录下的文件列表
     *
     * @param pathName 路径名字
     * @return FTP目录下的文件列表
     */
    public String[] getFileNameList(String pathName) {
        try {
            return ftpClient.listNames(pathName);
        } catch (IOException e) {
            logger.error("get file [" + pathName + "] name list error", e);
            return null;
        }
    }

    /**
     * 删除FTP上的文件
     *
     * @param ftpDirAndFileName 路径开头不能加/，比如应该是test/filename1
     * @return true为上传成功
     */
    public boolean deleteFile(String ftpDirAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.deleteFile(ftpDirAndFileName);
        } catch (IOException e) {
            logger.error("delete file [" + ftpDirAndFileName + "] error", e);
            return false;
        }
    }

    /**
     * 删除FTP目录
     *
     * @param ftpDirectory 要删除的FTP目录
     * @return true为删除成功
     */
    public boolean deleteDirectory(String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.removeDirectory(ftpDirectory);
        } catch (IOException e) {
            logger.error("delete directory [" + ftpDirectory + "] error", e);
            return false;
        }
    }

    /**
     * 关闭链接
     */
    public void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            logger.info(String.format("close FTP：%s:%s", this.server, this.port));
        } catch (Exception e) {
            logger.info(String.format("close FTP：%s:%s error", this.server, this.port), e);
        }
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public static void main(String[] args) {
        FtpUtil f = new FtpUtil("192.168.162.100", 21, "uftp", "admin");
        try {
            if (f.open()) {
                String fileName = "测试2.txt";
                // 上传
                f.upload("d:/1.txt", fileName, "test1");

                // 遍历
                FTPFile[] list = f.getFileList("test1");
                for (FTPFile file : list) {
                    String name = file.getName();
                    System.out.println("--" + new String(name.getBytes(StandardCharsets.ISO_8859_1), "GB2312"));
                }

                // 只遍历指定目录下的文件名
                String[] names = f.getFileNameList("test1");
                for (String name : names) {
                    System.out.println(new String(name.getBytes(StandardCharsets.ISO_8859_1), "GB2312"));
                }

                // 下载
                boolean b = f.get("/test1/测试2.txt", "d:/text.txt");
                System.out.println(b);

                // 删除
                String ftpDirAndFileName = "test1/测试.txt";
                boolean be = f.deleteFile(new String(ftpDirAndFileName.getBytes(), StandardCharsets.ISO_8859_1));
                System.out.println(be);

                // 删除目录
                boolean delf = f.deleteDirectory("test1");
                System.out.println(delf);

                f.close();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
