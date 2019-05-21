package com.gorge4j.user.dto;

import java.io.Serializable;

/**
 * @Title: ModifyDTO.java
 * @Description: 用户修改数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

public class ModifyDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 名字 */
    private String name;
    /** 类型 */
    private String type;
    /** 原密码 */
    private String password;
    /** 新密码 */
    private String newPassword;
    /** 确认新密码 */
    private String confirmNewPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    /** 重写POJO类的toString方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "ModifyDTO [name=" + name + ", type=" + type + ", password=" + password + ", newPassword="
                + newPassword + ", confirmNewPassword=" + confirmNewPassword + "]";
    }

}
