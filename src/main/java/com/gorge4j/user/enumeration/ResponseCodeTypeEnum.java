package com.gorge4j.user.enumeration;

/**
 * @Title: ResponseCodeTypeEnum.java
 * @Description: 通用返回对象枚举类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-08 21:18:44
 * @version v1.0
 */

public enum ResponseCodeTypeEnum {

    // 返回码
    SUCCESS("0", "请求成功"), SYSTEM_BUSY("100", "系统繁忙"), REQUEST_TIME_OUT("300", "请求超时"), PARAMETER_ERROR("400",
            "参数错误"), NETWORK_ERROR("404", "网络异常"), DATA_NOT_EXISTS("600", "数据不存在"), FAILURE("999", "未知错误");

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回对象枚举类构造函数
     * 
     * @param code 返回码
     * @param message 返回信息
     */
    private ResponseCodeTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
