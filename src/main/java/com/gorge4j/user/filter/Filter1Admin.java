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
import com.gorge4j.user.constant.UserTypeConstant;

/**
 * @Title: Filter1Admin.java
 * @Description: 管理员鉴权过滤器，类名中的数字 1 代表多个过滤器执行时的顺序
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-22 22:58:40
 * @version v1.0
 */

@WebFilter(filterName = "/Filter1Admin", urlPatterns = {"/add", "/view", "/delete"})
public class Filter1Admin implements Filter {

    private Filter1Admin() {}

    @Override
    public void destroy() {
        // Do nothing.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(false);
        // pass the request along the filter chain
        if (httpSession != null && httpSession.getAttribute(CommonConstant.USER_NAME) != null
                && httpSession.getAttribute(CommonConstant.USER_TYPE) != null
                && UserTypeConstant.ADMIN.equals(httpSession.getAttribute(CommonConstant.USER_TYPE))) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        }

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Do nothing.
    }

}
