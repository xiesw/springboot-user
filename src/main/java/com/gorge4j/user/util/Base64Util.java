package com.gorge4j.user.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: Base64Util.java
 * @Description: Base64 加/解密工具类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-12 21:23:40
 * @version v1.0
 */

public class Base64Util {

    private static Logger log = LoggerFactory.getLogger(Base64Util.class);

    private Base64Util() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Base64 加密
     * 
     * @param encode 需加密的字符串
     * @return 加密后的结果
     */
    public static String base64Encrypt(String encode) {
        try {
            return Base64.getEncoder().encodeToString(encode.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * Base64 解密
     * 
     * @param decode 需解密的字符串
     * @return 解密后的结果
     */
    public static String base64Decrypt(String decode) {
        try {
            byte[] by = Base64.getDecoder().decode(decode.getBytes(StandardCharsets.UTF_8));
            return new String(by, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
