package com.gorge4j.user.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @Title: DruidStatFilter.java
 * @Description: Druid 监控服务过滤器
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-28 18:22:56
 * @version v1.0
 */

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") // 忽略资源
        })
public class DruidStatFilter extends WebStatFilter {

}
