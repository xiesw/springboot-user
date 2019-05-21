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
    
    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的内容 */
    private UrlConstant() {
        throw new IllegalStateException("Utility class");
    }
    
    /** 首页 URL */
    public static final String INDEX = "/index";
    
    /** 注册 URL */
    public static final String REGISTER = "/register";
    
    /** 登录 URL */
    public static final String LOGIN = "/login";
    
    /** 查看用户列表 URL */
    public static final String VIEW = "/view";
    
    /** 新增用户 URL */
    public static final String ADD = "/add";
    
    /** 修改用户信息 URL */
    public static final String MODIFY = "/modify";

}
