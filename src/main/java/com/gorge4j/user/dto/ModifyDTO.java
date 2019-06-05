package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Title: ModifyDTO.java
 * @Description: 用户信息修改数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@Data
public class ModifyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 名字 */
    private String name;
    /** 类型 */
    private String type;
    /** 原密码 */
    @NotBlank(message = "原密码不能为空")
    private String password;
    /** 新密码 */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    /** 确认新密码 */
    @NotBlank(message = "确认新密码不能为空")
    private String confirmNewPassword;

}
