package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** 重写 POJO 类的 toString 方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "LoginDTO [name=" + name + ", password=" + password + ", type=" + type + "]";
    }

}
