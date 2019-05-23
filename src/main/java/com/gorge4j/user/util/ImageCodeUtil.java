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

/**
 * @Title: ImageCodeUtil.java
 * @Description: 生成图形验证码
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

    /** 验证码默认宽度 */
    private static final int IMAGE_WIDTH = 60;
    /** 验证码默认高度 */
    private static final int IMAGE_HEIGHT = 20;
    /** 随机干扰线 */
    private static final int RAND_COLOR = 168;
    /** 颜色数值的上限 */
    private static final int COLOR_MAX = 255;

    /** 可根据需要来修改，生成纯数字或者纯字母，或者默认这种混合的 */
    private static char[] mapTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成图形验证码
     * 
     * @param width 宽度
     * @param height 高度
     * @param verifySize 验证码个数
     * @return
     */
    public static Map<String, Object> getImageCode(int width, int height, int verifySize)
            throws NoSuchAlgorithmException {
        Map<String, Object> returnMap = new HashMap<>(6);
        if (width <= 0) {
            width = IMAGE_WIDTH;
        }
        if (height <= 0) {
            height = IMAGE_HEIGHT;
        }
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
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < RAND_COLOR; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的码
        StringBuilder strEnsure = new StringBuilder();
        // 4代表4位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < verifySize; ++i) {
            strEnsure.append(mapTable[random.nextInt(mapTable.length)]);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 直接生成
            String str = strEnsure.substring(i, i + 1);
            g.drawString(str, 13 * i + 6, 16);
        }
        // 释放图形上下文
        g.dispose();
        returnMap.put("image", image);
        returnMap.put("strEnsure", strEnsure.toString());
        return returnMap;
    }

    /** 给定范围获得随机颜色 */
    private static Color getRandColor(int fc, int bc) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        if (fc > COLOR_MAX) {
            fc = COLOR_MAX;
        }
        if (bc > COLOR_MAX) {
            bc = COLOR_MAX;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
