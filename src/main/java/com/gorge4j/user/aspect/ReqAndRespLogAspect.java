package com.gorge4j.user.aspect;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: ReqAndRespLogAspect.java
 * @Description: Controller 层全局请求及响应日志的打印
 * @Copyright: © 2019 ***
 * @Company: ***
 *
 * @author gorge.guo
 * @date 2019-05-08 21:12:12
 * @version v1.0
 */

@Slf4j
@Component
@Aspect
public class ReqAndRespLogAspect {

    /**
     * 请求参数的日志打印
     * 
     * @param joinPoint 切点
     */
    @Before("within(com.gorge4j.user.controller.*)")
    public void before(JoinPoint joinPoint) {
        // 获取传入目标方法的参数对象
        Object[] args = joinPoint.getArgs();
        // 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法对象
        Method method = methodSignature.getMethod();
        // 分割组装参数
        String strArgs = StringUtils.join(args, ",");
        log.info("{}.{} - 请求参数: {}", method.getDeclaringClass().getName(), method.getName(), strArgs);
    }

    /**
     * 响应结果的日志打印
     * 
     * @param joinPoint 切点
     * @param res 响应结果对象
     */
    @AfterReturning(value = "within(com.gorge4j.user.controller.*)", returning = "res")
    public void after(JoinPoint joinPoint, Object res) {
        // 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法对象
        Method method = methodSignature.getMethod();
        // 将返回对象转换成 Json 字符串，项目的返回如果不是 Json 的数据格式，那么返回参数就只有 URL
        String strRes = JSONUtils.toJSONString(res);
        log.info("{}.{} - 返回结果: {}", method.getDeclaringClass().getName(), method.getName(), strRes);
    }

}
