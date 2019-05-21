package com.gorge4j.user.vo;

import java.io.Serializable;

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

public class ResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 返回码 */
    private String code;
    /** 返回信息 */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** 重写POJO类的toString方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "ResponseVO [code=" + code + ", message=" + message + "]";
    }

}
