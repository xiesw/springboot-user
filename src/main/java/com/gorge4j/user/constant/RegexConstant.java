package com.gorge4j.user.constant;

/**
 * @Title: RegexConstant.java
 * @Description: 正则常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-06-24 21:07:43
 * @version v1.0
 */

public class RegexConstant {
    
    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的值 */
    private RegexConstant() {
        throw new IllegalStateException("Utility class");
    }
    
    /** 用户名要求只能是小写字母及数字 */
    public static final String REGEX_NAME = "^[a-z|0-9]{30}$";
    
    /** 密码要求六位纯数字 */
    public static final String REGEX_PASS = "^\\d{6}$";

}
