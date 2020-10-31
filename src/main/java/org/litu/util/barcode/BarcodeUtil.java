package org.litu.util.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;

/**
 * 条码生成
 */
public class BarcodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "JPG";
    // 条码宽度
    private static final int BARCODE_WIDTH = 200;
    // 条码高度
    private static final int BARCODE_HEIGHT = 50;

    /**
     *
     * 生成二维码转成的图片
     * @param content
     *                 发送的字符串
     * @return  二维码转成的图片
     * @throws Exception 抛出异常给用户端
     */
    private static BufferedImage createImage(String content) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        // 文字编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, BARCODE_WIDTH, BARCODE_HEIGHT, hints);

        // 将生成的二维码转为图片
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 生成条码
     *
     * @param content  内容
     * @param destPath 存放目录
     * @param fileName 条码文件名
     * @throws Exception  抛出异常
     */
    public static String encode(String content, String destPath, String fileName) throws Exception {
        BufferedImage image = createImage(content);
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.indexOf(".")) + "." + FORMAT.toLowerCase();
        } else {
            fileName = fileName + FORMAT.toLowerCase();
        }
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码(内嵌LOGO) 二维码文件名随机，文件名可能会有重复
     *
     * @param content  内容
     * @param destPath 存放目录
     * @throws Exception 抛出异常
     */
    public static String encode(String content, String destPath) throws Exception {
        BufferedImage image = createImage(content);
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));

        return fileName;
    }

    /**
     * 测试
     * @param args  args参数
     * @throws Exception  抛出异常
     */
    public static void main(String[] args) throws Exception {
        String msg = "10000001111";
        String path = "D:\\2.png";
        encode(msg, path);
    }
}