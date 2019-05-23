package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * @Title: DeleteDTO.java
 * @Description: 用户删除数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

public class DeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 用户id */
    @NotNull(message = "用户id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 重写POJO类的toString方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "DeleteDTO [id=" + id + "]";
    }

}
