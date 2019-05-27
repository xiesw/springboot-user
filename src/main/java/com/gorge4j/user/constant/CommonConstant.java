package com.gorge4j.user.constant;

/**
 * @Title: CommonConstant.java
 * @Description: 公共静态常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-28 21:44:16
 * @version v1.0
 */

public class CommonConstant {

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的值 */
    private CommonConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 以下是请求结果返回的字段名称的一些常量
     */

    /** 统一返回对象的名称 */
    public static final String RESPONSE_VO = "responseVO";
    /** 用户列表返回对象的名称 */
    public static final String LST_VIEW_VOS = "lstViewVOs";
    /** Session 中的用户名常量的字段名称，多处使用统一在这里定义，避免 SonarLint 魔法值的告警 */
    public static final String USER_NAME = "name";
    /** Session 中的用户类型常量的字段名称，多处使用统一在这里定义，避免 SonarLint 魔法值的告警 */
    public static final String USER_TYPE = "type";
    /** 用户类型中文转码后的名称常量 */
    public static final String USER_TYPE_NAME = "typeName";

    /**
     * 以下是图形验证码相关的常量
     */

    /** 验证码生成的时间 */
    public static final String CODE_TIME = "codeTime";
    /** 验证码过期时间，单位：分钟 */
    public static final int CAPTCHA_EXPIRY_TIME = 5;
    /** 秒/毫秒换算 */
    public static final int MILLISECOND_CONVERSION = 1000;
    /** 分钟/秒换算 */
    public static final int MINUTE_CONVERSION = 60;
    /** 生成验证码的变量名称 */
    public static final String SIMPLE_CAPTCHA = "simpleCaptcha";
    /** 生成验证码的变量值 */
    public static final String CAPTCHA_VALUE = "captchaValue";
    /** 生成的验证码图形的变量名 */
    public static final String IMAGE = "image";
    /** 生成的图形验证码文件格式化的名称 */
    public static final String JPEG = "JPEG";
    /** 图片字符涉及的罗马字体名 */
    public static final String TIMES_NEW_ROMAN = "Times New Roman";

}
