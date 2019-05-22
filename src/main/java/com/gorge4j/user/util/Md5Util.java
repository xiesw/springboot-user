package com.gorge4j.user.util;

import java.security.MessageDigest;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title: MD5Util.java
 * @Description: MD5 加密工具类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-16 00:04:58
 * @version v1.0
 */

public class Md5Util {

    private Md5Util() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * md5 加密通用工具类
     * 
     * @param encode 需加密的字符串
     * @return md5 加密后的字符串（32位字符串）
     */
    public static final String md5(String encode) {
        // 检验参数是否是 null 或者 ""
        if (StringUtils.isBlank(encode)) {
            return "";
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            // 设置字符集
            byte[] strTemp = encode.getBytes("utf-8");
            // 生成一个 md5 加密计算摘要
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // 计算 md5 函数
            mdTemp.update(strTemp);
            // digest() 最后确定返回 md5 hash 值，返回值为 8 位字符串。因为 md5 hash 值是 16 位的 hex 值，实际上就是 8 位的字符
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                // 不带符号右移四位(不管 byte0 的类型 位移处补 0)，& 十六进制的 f 即 高四位清空 取低四位的值，>>> 优先级高于 &
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

}
