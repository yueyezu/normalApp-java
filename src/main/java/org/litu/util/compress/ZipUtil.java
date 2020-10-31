package org.litu.util.compress;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * 解压压缩工具类
 * 功能:对文件进行压缩，解压
 */
public class ZipUtil {
    private static final int BUFFEREDSIZE = 1024;

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param filePath    需要压缩的文件夹或者文件路径
     * @param isDelete    是否删除源文件
     * @throws Exception 抛出异常
     */
    public static void zip(String zipFileName, String filePath, boolean isDelete) throws Exception {
        zip(zipFileName, new File(filePath), isDelete);
    }

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param inputFile   需要压缩的文件夹或者文件
     * @param isDelete    是否删除源文件
     * @throws Exception 抛出异常
     */
    @SuppressWarnings("resource")
    public static void zip(String zipFileName, File inputFile, boolean isDelete) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        if (!inputFile.exists()) {
            throw new FileNotFoundException("在指定路径未找到需要压缩的文件！");
        }
        zip(out, inputFile, "", isDelete);
        out.close();
    }

    /**
     * 递归压缩方法
     *
     * @param out       压缩包输出流
     * @param inputFile 需要压缩的文件
     * @param base      压缩的路径
     * @param isDelete  是否删除源文件
     * @throws Exception 抛出异常
     */
    private static void zip(ZipOutputStream out, File inputFile, String base, boolean isDelete) throws Exception {
        if (inputFile.isDirectory()) { // 如果是目录
            File[] inputFiles = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < inputFiles.length; i++) {
                zip(out, inputFiles[i], base + inputFiles[i].getName(), isDelete);
            }
        } else { // 如果是文件
            if (base.length() > 0) {
                out.putNextEntry(new ZipEntry(base));
            } else {
                out.putNextEntry(new ZipEntry(inputFile.getName()));
            }
            try (FileInputStream in = new FileInputStream(inputFile)) {
                int len;
                byte[] buff = new byte[BUFFEREDSIZE];
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
            } catch (IOException e) {
                throw e;
            }
        }
        if (isDelete) {
            inputFile.delete();
        }
    }

    /**
     * 解压缩
     *
     * @param zipFilePath  压缩包路径
     * @param fileSavePath 解压路径
     * @param isDelete     是否删除源文件
     * @throws Exception 抛出异常
     */
    public static void unZip(String zipFilePath, String fileSavePath, boolean isDelete) throws Exception {
        try {
            (new File(fileSavePath)).mkdirs();
            File f = new File(zipFilePath);
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            ZipFile zipFile = new ZipFile(f, Charset.forName("gbk")); //加编码是为了解决中文问题。
            String strPath, gbkPath, strtemp;
            File tempFile = new File(fileSavePath);// 从当前目录开始
            strPath = tempFile.getAbsolutePath();// 输出的绝对位置
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                }
                // 读写文件
                InputStream is = zipFile.getInputStream(zipEnt);
                BufferedInputStream bis = new BufferedInputStream(is);
                gbkPath = zipEnt.getName();
                strtemp = strPath + File.separator + gbkPath;
                // 建目录
                String strsubdir = gbkPath;
                for (int i = 0; i < strsubdir.length(); i++) {
                    if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                        String temp = strPath + File.separator + strsubdir.substring(0, i);
                        File subdir = new File(temp);
                        if (!subdir.exists())
                            subdir.mkdir();
                    }
                }
                //写入文件
                FileOutputStream fos = new FileOutputStream(strtemp);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int len;
                byte[] buff = new byte[BUFFEREDSIZE];
                while ((len = bis.read(buff)) != -1) {
                    bos.write(buff, 0, len);
                }
                bos.close();
                fos.close();
            }
        } catch (Exception e) {
            throw e;
        }
        if (isDelete) {
            new File(zipFilePath).delete();
        }
    }

    // 测试
    public static void main(String[] args) {
        ZipUtil cpr = new ZipUtil();
        try {
            // 参数1：解压的格式 参数2:要压缩的的文件
            cpr.zip("D:\\photo\\1111.zip", "D:\\photo\\1111", false);
            // 参数1：要解压的类型 参数2:执行解压的文件
            cpr.unZip("D:\\photo\\1111.zip", "D:\\photo\\1111\\222", false);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}