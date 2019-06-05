package com.gorge4j.user.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * @Title: UserVO.java
 * @Description: 用户信息返回值对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户名字 */
    private String name;
    /** 用户类型 */
    private String type;

}
