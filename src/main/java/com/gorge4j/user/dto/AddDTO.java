package com.gorge4j.user.dto;

import java.io.Serializable;

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
    /** 名字 */
    private String name;
    /** 密码 */
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

    /** 重写POJO类的toString方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "AddDTO [name=" + name + ", password=" + password + "]";
    }

}
