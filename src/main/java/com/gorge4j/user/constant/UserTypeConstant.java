package com.gorge4j.user.constant;

/**
 * @Title: UserTypeConstant.java
 * @Description: 用户类型静态常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:49:29
 * @version v1.0
 */

public final class UserTypeConstant {

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的值 */
    private UserTypeConstant() {
        throw new IllegalStateException("Utility class");
    }

    /** 管理员用户 */
    public static final String ADMIN = "A";

    /** 普通用户 */
    public static final String ORDINARY = "O";

    /**
     * 用户类型代码转描述
     * 
     * @param type 用户类型
     * @return 用户类型中文描述
     */
    public static String typeToDesc(String type) {
        if (UserTypeConstant.ADMIN.equals(type)) {
            return "管理员";
        } else if (UserTypeConstant.ORDINARY.equals(type)) {
            return "普通用户";
        } else {
            return "未知身份，请联系系统管理员";
        }
    }

}
