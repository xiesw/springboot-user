package com.gorge4j.user.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @Title: Swagger2Filter.java
 * @Description: Swagger 资源过滤器
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-28 18:22:56
 * @version v1.0
 */

@WebFilter(filterName = "swagger2Filter", urlPatterns = "/*", initParams = {@WebInitParam(name = "exclusions",
        value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/swagger-ui.html/*,/v2/api-docs") // 忽略资源
})
public class Swagger2Filter extends WebStatFilter {

}
