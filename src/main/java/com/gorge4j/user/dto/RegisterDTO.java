package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: RegisterDTO.java
 * @Description: 注册数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@ApiModel("用户注册实体")
@Data
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    @ApiModelProperty("用户 name")
    @NotBlank(message = "用户名不能为空")
    private String name;
    /** 用户密码 */
    @ApiModelProperty("用户 password")
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
