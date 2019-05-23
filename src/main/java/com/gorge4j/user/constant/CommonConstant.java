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

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的内容 */
    private CommonConstant() {
        throw new IllegalStateException("Utility class");
    }

    /** 验证码过期时间 */
    public static final int CAPTCHA_EXPIRY_TIME = 5;
    /** 毫秒换算 */
    public static final int MILLISECOND_CONVERSION = 1000;
    /** 分钟换算 */
    public static final int MINUTE_CONVERSION = 60;
    /** 统一返回对象名称 */
    public static final String RESPONSE_VO = "responseVO";
    /** 用户列表返回对象名称 */
    public static final String LST_VIEW_VOS = "lstViewVOs";
    /** session中的用户名常量，避免魔法值的告警 */
    public static final String USER_NAME = "name";
    /** session中的用户类型常量，避免魔法值的告警 */
    public static final String USER_TYPE = "type";
    /** 管理员用户名称常量 */
    public static final String USER_ADMIN = "admin";

}
