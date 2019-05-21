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

    /** 统一返回对象名称 */
    public static final String RESPONSE_VO = "responseVO";
    /** 用户列表返回对象名称 */
    public static final String LST_VIEW_VOS = "lstViewVOs";

}
