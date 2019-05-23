package com.gorge4j.user.handler;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @Title: GlobalExceptionHandler.java
 * @Description: 全局异常处理类，有多个 @ExceptionHandler 注解的方法时，会从当前的异常类一直往上找父类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-08 00:23:55
 * @version v1.0
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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

        Map<String, String> fieldAndMessage = new HashMap<>(16);
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldAndMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }

        String strField = sb.toString();

        response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
        response.setMessage(ResponseCodeTypeEnum.PARAMETER_ERROR.getMessage());
        // 方便调试，将异常统一打印出来
        log.warn("SpringMVC层参数校验异常: {}", strField);

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

        response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
        response.setMessage(ResponseCodeTypeEnum.PARAMETER_ERROR.getMessage());
        // 定义异常
        String strMessage = exception != null ? exception.getMessage() : null;
        // 方便调试，将异常统一打印出来
        log.warn("Java原生参数校验异常: {}", strMessage);

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

        Map<String, String> fieldAndMessage = new HashMap<>(16);
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldAndMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }

        String strField = sb.toString();

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

        response.setCode(ResponseCodeTypeEnum.FAILURE.getCode());
        if (exception != null) {
            response.setMessage(exception.getMessage());
        } else {
            response.setMessage(ResponseCodeTypeEnum.FAILURE.getMessage());
        }
        // 定义未知异常
        String strMessage = exception != null ? exception.getMessage() : null;
        // 方便调试，将异常统一打印出来
        log.warn("其它未知异常: {}", strMessage);

        return response;
    }

}
