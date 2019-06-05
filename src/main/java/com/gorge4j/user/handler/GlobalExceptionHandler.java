package com.gorge4j.user.handler;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.gorge4j.user.enumeration.ResponseCodeTypeEnum;
import com.gorge4j.user.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: GlobalExceptionHandler.java
 * @Description: 全局异常处理类，有多个 @ExceptionHandler 注解的方法时，会从当前的异常类一直往上找父类，最多到 Exception 异常类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-08 00:23:55
 * @version v1.0
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * SpringMVC层参数的校验异常
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO resolveMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ResponseVO response = new ResponseVO();
        // 定义字段及校验异常信息的 key/value 结构
        Map<String, String> fieldAndMessage = new HashMap<>(16);
        StringBuilder sb = new StringBuilder();
        // 组装字段及校验异常信息
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldAndMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }
        String strField = sb.toString();
        // 组装异常信息
        response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
        response.setMessage(ResponseCodeTypeEnum.PARAMETER_ERROR.getMessage());
        // 方便调试，将异常统一打印出来
        log.warn("SpringMVC 层参数校验异常: {}", strField);

        return response;
    }

    /**
     * Java原生参数校验异常
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO resolveValidationException(ValidationException exception) {
        ResponseVO response = new ResponseVO();
        // 组装异常信息
        response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
        response.setMessage(ResponseCodeTypeEnum.PARAMETER_ERROR.getMessage());
        // 定义异常
        String strMessage = exception != null ? exception.getMessage() : null;
        // 方便调试，将异常统一打印出来
        log.warn("Java 原生参数校验异常: {}", strMessage);

        return response;
    }

    /**
     * SpringMVC自带的参数绑定校验的异常
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO resolveBindException(BindException exception) {
        ResponseVO response = new ResponseVO();
        // 定义字段及校验异常信息的 key/value 结构
        Map<String, String> fieldAndMessage = new HashMap<>(16);
        StringBuilder sb = new StringBuilder();
        // 组装字段及校验异常信息
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldAndMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }
        String strField = sb.toString();
        // 组装异常返回信息
        response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
        response.setMessage(ResponseCodeTypeEnum.PARAMETER_ERROR.getMessage() + " : " + strField);
        // 方便调试，将异常统一打印出来
        log.warn("SpringMVC自带的参数绑定校验异常: {}", strField);

        return response;
    }

    /**
     * 其它统一报未知错误
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO resolveException(Exception exception) {
        ResponseVO response = new ResponseVO();
        // 组装异常返回信息
        response.setCode(ResponseCodeTypeEnum.FAILURE.getCode());
        response.setMessage(exception != null ? exception.getMessage() : ResponseCodeTypeEnum.FAILURE.getMessage());
        // 定义未知异常
        String strMessage = exception != null ? exception.getMessage() : ResponseCodeTypeEnum.FAILURE.getMessage();
        // 方便调试，将异常统一打印出来
        log.warn("其它未知异常: {}", strMessage);

        return response;
    }

}
