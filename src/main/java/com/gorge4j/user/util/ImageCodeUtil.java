package com.gorge4j.user.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.gorge4j.user.constant.CommonConstant;

/**
 * @Title: ImageCodeUtil.java
 * @Description: 图形验证码生成工具类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-28 00:04:58
 * @version v1.0
 */

public class ImageCodeUtil {

    private ImageCodeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /** 生成验证码图片的默认宽度 */
    private static final int IMAGE_WIDTH = 60;
    /** 生成验证码图片的默认高度 */
    private static final int IMAGE_HEIGHT = 20;
    /** 随机干扰线的条数，太稀疏或者太密织都达不到干扰的效果 */
    private static final int RAND_COLOR = 168;
    /** 颜色数值的上限，三原色的范围都是 [0, 255] 范围 */
    private static final int COLOR_MAX = 255;
    /** 可根据需要来修改，生成纯数字或者纯字母，或者默认这种混合的 */
    private static char[] mapTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成图形验证码
     * 
     * @param width 生成的图形验证码的宽度
     * @param height 生成的图形验证码的高度
     * @param verifySize 需要生成的验证码位数
     * @return 验证码的图片对象及验证码的值
     */
    public static Map<String, Object> getImageCode(int width, int height, int verifySize)
            throws NoSuchAlgorithmException {
        Map<String, Object> returnMap = new HashMap<>(6);
        // 确保验证码的宽度和高度不能小于或等于零
        width = width <= 0 ? IMAGE_WIDTH : width;
        height = height <= 0 ? IMAGE_HEIGHT : height;
        // BufferedImage 是 Image 的一个子类，Image 和 BufferedImage
        // 主要作用就是将一副图片加载到内存中，BufferedImage 生成的图片在内存里有一个图像缓冲区
        // 利用这个缓冲区我们可以很方便的操作这个图片，通常用来做图片修改操作如大小变换、图片变灰、设置图片透明或不透明等
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = SecureRandom.getInstanceStrong();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        // 画长方形
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font(CommonConstant.TIMES_NEW_ROMAN, Font.PLAIN, 18));
        // 再次设置背景色，实测跟前一次设置背景色有叠加的效果，会让背景颜色看起来更复杂
        g.setColor(getRandColor(160, 200));
        // 随机产生 168 条干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < RAND_COLOR; i++) {
            // nextInt(n) 会返回 [0, n) 之间的随机数
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            // x: 线段起点的横坐标；y: 线段起点的纵坐标；x + xl: 线段终点的横坐标；y + yl: 线段终点的纵坐标
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 定义验证码的值
        StringBuilder captchaValue = new StringBuilder();
        // 4代表验证码位数，如果要生成更多位的认证码，则加大数值
        for (int i = 0; i < verifySize; ++i) {
            captchaValue.append(mapTable[random.nextInt(mapTable.length)]);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 直接生成
            String str = captchaValue.substring(i, i + 1);
            // 使用此图形上下文的当前字体和颜色绘制由指定 string 给定的文本
            g.drawString(str, 13 * i + 6, 16);
        }
        // 释放图形上下文资源
        g.dispose();
        // 组装图像结果及验证码值并返回
        returnMap.put(CommonConstant.IMAGE, image);
        returnMap.put(CommonConstant.CAPTCHA_VALUE, captchaValue.toString());
        return returnMap;
    }

    /** 给定范围获得随机颜色 */
    private static Color getRandColor(int fc, int bc) throws NoSuchAlgorithmException {
        // 创建一个随机数生成对象的实例
        Random random = SecureRandom.getInstanceStrong();
        // 颜色值不能超过最大范围
        fc = fc > COLOR_MAX ? COLOR_MAX : fc;
        bc = bc > COLOR_MAX ? COLOR_MAX : bc;
        // nextInt(n) 会返回 [0, n) 之间的随机数
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        // 按随机生成的 r、g、b 值设置颜色并返回
        return new Color(r, g, b);
    }
}
