package com.gorge4j.user.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * @Title: ResponseVO.java
 * @Description: 统一返回值对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 返回码 */
    private String code;
    /** 返回信息 */
    private String message;

}
