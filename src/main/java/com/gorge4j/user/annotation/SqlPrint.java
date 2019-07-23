package com.gorge4j.user.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Title: SqlPrint.java
 * @Description: SQL 语句打印注解类，无元素，标记注解的实例
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-07-16 23:53:48
 * @version v1.0
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface SqlPrint {

}
