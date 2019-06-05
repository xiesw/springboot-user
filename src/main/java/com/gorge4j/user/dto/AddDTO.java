package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Title: AddDTO.java
 * @Description: 添加用户数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class AddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /** 用户密码 */
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
