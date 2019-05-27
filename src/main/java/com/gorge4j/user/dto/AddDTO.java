package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

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

public class AddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /** 用户密码 */
    @NotBlank(message = "用户密码不能为空")
    private String password;

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

    /** 重写 POJO 类的 toString 方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "AddDTO [name=" + name + ", password=" + password + "]";
    }

}
