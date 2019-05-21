package com.gorge4j.user.constant;

/**
 * @Title: ResponseConstant.java
 * @Description: 公共返回对象静态常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-28 21:44:16
 * @version v1.0
 */

public final class ResponseConstant {

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的内容 */
    private ResponseConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUCCESS = "0";

    public static final String FAIL = "99";

}
