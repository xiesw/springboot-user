package com.gorge4j.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.gorge4j.user.constant.CommonConstant;

/**
 * @Title: Filter0Session.java
 * @Description: 通用鉴权过滤器，类名中的数字 0 代表多个过滤器执行时的顺序
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-22 22:58:40
 * @version v1.0
 */

@WebFilter(filterName = "/Filter0Session", urlPatterns = {"/index", "/add", "/modify", "/view", "/delete", "/loginOut"})
public class Filter0Session implements Filter {

    private Filter0Session() {}

    /**
     * 在web应用程序启动时，web服务器将根据 web.xml文件中的配置信息来创建每个注册的Filter实例对象，并将其保存在服务器的内存中。Init方法在Filter生命周期中仅执行一次。
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Do nothing.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(false);
        // pass the request along the filter chain
        if (httpSession != null && httpSession.getAttribute(CommonConstant.USER_NAME) != null) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        }
    }

    /** 在Web容器卸载 Filter 对象之前被调用。该方法在Filter的生命周期中仅执行一次。在这个方法中，可以释放过滤器使用的资源。 */
    @Override
    public void destroy() {
        // Do nothing.
    }

}
