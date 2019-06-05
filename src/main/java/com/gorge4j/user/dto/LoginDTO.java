package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Title: LoginDTO.java
 * @Description: 用户登录数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 名字 */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /** 密码 */
    @NotBlank(message = "用户密码不能为空")
    private String password;
    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String checkCode;
    /** 类型 */
    @NotBlank(message = "用户类型不能为空")
    private String type;

}
