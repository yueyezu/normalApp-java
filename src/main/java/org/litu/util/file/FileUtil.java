package org.litu.util.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.litu.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * 文件工具类
 * 功能:对文件,文件夹的一些读取,创建，删除,复制
 */
public class FileUtil {
    public static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    // TODO 该工具类的获取路径的实现，转移到业务层。
    private final static String TEMP_FILE_DIR = "temp/";

    private final static String FILE_PATH_PATTERN = "YYYY-MM/";

    /**
     * 读取文件内容
     *
     * @param is 输入的文件
     * @return 读取文件内容
     */
    public static String readFile(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String readLine;
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (Exception e) {
            logger.error("读取文件错误", e);
        }
        return sb.toString();
    }

    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName 文件名
     * @return true则为文件存在
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
     * 注意：可能会在返回false的时候创建部分父目录。
     *
     * @param file 文件实例
     * @return true则为存在父目录
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * 返回文件的URL地址。
     *
     * @param file 文件实例
     * @return 文件URL
     * @throws MalformedURLException 抛出URL格式错误
     */
    public static URL getURL(File file) throws MalformedURLException {
        String fileURL = "file:/" + file.getAbsolutePath();
        return new URL(fileURL);
    }

    /**
     * 从文件名得到文件绝对路径。
     *
     * @param fileName 文件名
     * @return 文件绝对路径
     */
    public static String getFilePath(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }

    /**
     * @param path
     * @return
     */
    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.contains(":")) {
            return true;
        }
        return false;
    }

    /**
     * 从文件路径得到文件名。
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * 获取文件名字的后缀
     *
     * @param filePath 文件路径
     * @return 文件后缀
     */
    public static String getFileSuffix(String filePath) {
        int index = filePath.lastIndexOf(".");
        if (index > 0) {
            return filePath.substring(index + 1);
        }
        return null;
    }

    /**
     * 将文件名中的类型部分去掉。
     *
     * @param filename 文件名
     * @return 文件名，没有文件类型
     */
    public static String removeFileSuffix(String filename) {
        int index = filename.lastIndexOf(".");
        System.out.println("index:" + index);
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    /**
     * 删除一个文件。
     *
     * @param filename 文件名
     * @throws IOException 抛出IO异常
     */
    public static void deleteFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.isDirectory()) {
            throw new IOException("IOException -> BadInputException: not a file.");
        }
        if (!file.exists()) {
            throw new IOException("IOException -> BadInputException: file is not exist.");
        }
        if (!file.delete()) {
            throw new IOException("Cannot delete file. filename = " + filename);
        }
    }

    /**
     * 删除文件夹下的所有文件夹
     *
     * @param dirFile 文件实例
     * @return dirFile.delete();
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : Objects.requireNonNull(dirFile.listFiles())) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    /**
     * 复制文件
     *
     * @param src 文件实例1，被复制文件
     * @param dst 文件实例2，复制文件
     * @throws Exception 抛出异常
     */
    public static void copyFile(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;

        try (InputStream in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
             OutputStream out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param filePath 文件路径的字符串表示形式
     * @param KeyWords 查找包含某个关键字的信息：非null为带关键字查询；null为全文显示
     * @return 当文件存在时，返回字符串；当文件不存在时，返回null
     */
    public static String readFromFile(String filePath, String KeyWords) {
        StringBuffer stringBuffer = null;
        File file = new File(filePath);
        if (file.exists()) {
            stringBuffer = new StringBuffer();
            String temp;
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((temp = bufferedReader.readLine()) != null) {
                    if (KeyWords == null) {
                        stringBuffer.append(temp).append("\n");
                    } else {
                        if (temp.contains(KeyWords)) {
                            stringBuffer.append(temp).append("\n");
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("文件读取错误", e);
            }
        }
        if (stringBuffer == null) {
            return null;
        } else {
            return stringBuffer.toString();
        }
    }

    /**
     * 将指定字符串写入文件。如果给定的文件路径不存在，将新建文件后写入。
     *
     * @param log      要写入文件的字符串
     * @param filePath 文件路径的字符串表示形式，目录的层次分隔可以是“/”也可以是“\\”
     * @param isAppend true：追加到文件的末尾；false：以覆盖原文件的方式写入
     */

    public static boolean writeIntoFile(String log, String filePath, boolean isAppend) {
        boolean isSuccess = true;
        //先过滤掉文件名
        int index = filePath.lastIndexOf("\\");
        String dir = filePath.substring(0, index);
        //创建除文件的路径
        File fileDir = new File(dir);
        fileDir.mkdirs();
        //再创建路径下的文件
        File file = null;
        try {
            file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            isSuccess = false;
        }
        //将logs写入文件

        try (FileWriter fileWriter = new FileWriter(file, isAppend);) {
            fileWriter.write(log);
            fileWriter.flush();
        } catch (IOException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * 创建文件，如果该文件已存在将不再创建（即不起任何作用）
     *
     * @param filePath 要创建文件的路径的字符串表示形式，目录的层次分隔可以是“/”也可以是“\\”
     * @return 创建成功将返回true；创建不成功则返回false
     */
    public static boolean createNewFile(String filePath) {
        boolean isSuccess = true;
        //如有则将"\\"转为"/",没有则不产生任何变化
        String filePathTurn = filePath.replaceAll("\\", "/");
        //先过滤掉文件名
        int index = filePathTurn.lastIndexOf("/");
        String dir = filePathTurn.substring(0, index);
        //再创建文件夹
        File fileDir = new File(dir);
        //创建文件
        File file = new File(filePathTurn);
        try {
            isSuccess = file.createNewFile();
        } catch (IOException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    /*------------- start 业务相关封装 ----------------*/

    /**
     * 获取存储用文件的相对路径,带后缀则为文件，不带后缀则为路径
     *
     * @param fileType 文件类型, 获取路径，则传入：”“
     * @return
     */
    public static String getSavePath(String fileType) {
        // 文件名称、路径的处理
        String dirName = DateFormatUtils.format(new Date(), FILE_PATH_PATTERN);

        fileType = StringUtils.isBlank(fileType) ? "" : ("." + fileType);

        String saveFileName = newFileName() + fileType;
        String relativePath = dirName + saveFileName;    // 相对路径

        return relativePath;
    }

    /**
     * 获取临时存储用文件的相对路径 ,带后缀则为文件，不带后缀则为路径
     *
     * @param fileType 文件类型, 获取路径，则传入：”“
     * @return
     */
    public static String getTempPath(String fileType) {
        // 文件名称、路径的处理
        fileType = StringUtils.isBlank(fileType) ? "" : ("." + fileType);

        String saveFileName = newFileName() + fileType;
        String tempPath = TEMP_FILE_DIR + saveFileName;    // 相对路径

        return tempPath;
    }

    /**
     * 新的文件名称生成
     *
     * @return
     */
    private static String newFileName() {
        return Util.GuId32();
    }

    /*--------------- end 业务相关封装 --------------*/
}