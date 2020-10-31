package org.litu.util.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成工具类
 */
public class CaptchaUtil {
    private BufferedImage image;
    private String str;
    private static char[] code = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();

    private CaptchaUtil() {
        this.init();
    }

    public static CaptchaUtil Instance() {
        return new CaptchaUtil();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public String getString() {
        return this.str;
    }

    //设置验证码格式
    private void init() {
        int width = 85;
        int height = 20;
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(this.getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(this.getRandColor(160, 200));
        //生成随机坐标
        for (int i = 0; i < 155; ++i) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, i + x1, y + y1);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            //随机字体大小
            String rand = String.valueOf(code[random.nextInt(code.length)]);
            sRand.append(rand);
            //颜色随机生成
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 14, 16);
        }

        this.str = sRand.toString();
        g.dispose();
        this.image = image;
    }

    /**
     * 如果rgb大于255则进行修正
     *
     * @param fc 前景色
     * @param bc 后景色
     * @return 新的rgb颜色
     */
    public Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 主函数，调用提醒
     *
     * @param args args参数
     */
    public static void main(String[] args) {
        CaptchaUtil util = CaptchaUtil.Instance();
        String str = util.getString();
        System.out.println(str);
    }
}