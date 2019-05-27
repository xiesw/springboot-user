package com.gorge4j.user.constant;

/**
 * @Title: UrlConstant.java
 * @Description: URL 静态常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:49:29
 * @version v1.0
 */

public class UrlConstant {

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的值 */
    private UrlConstant() {
        throw new IllegalStateException("Utility class");
    }

    /** 跳转首页 URL */
    public static final String INDEX = "/index";

    /** 跳转注册 URL */
    public static final String REGISTER = "/register";

    /** 跳转登录 URL */
    public static final String LOGIN = "/login";

    /** 跳转查看用户列表 URL */
    public static final String VIEW = "/view";

    /** 跳转新增用户 URL */
    public static final String ADD = "/add";

    /** 跳转修改用户信息 URL */
    public static final String MODIFY = "/modify";

}
