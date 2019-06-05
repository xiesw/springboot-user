package com.gorge4j.user.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Title: ViewVO.java
 * @Description: 用户列表返回值对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class ViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Integer id;
    /** 用户名 */
    private String name;
    /** 用户类型 */
    private String type;
    /** 用户创建时间 */
    private Date gmtCreate;

}
