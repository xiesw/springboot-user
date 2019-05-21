package com.gorge4j.user.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.gorge4j.user.constant.CommonConstant;
import com.gorge4j.user.constant.ResponseConstant;
import com.gorge4j.user.constant.UrlConstant;
import com.gorge4j.user.constant.UserTypeConstant;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;

/**
 * @Title: UserController.java
 * @Description: 用户管理系统控制层
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-08 22:58:40
 * @version v1.0
 */

@Controller
public class UserController {

    /** 自动注入 userService，用来处理业务，按需要注入具体的实现，存在多个实现切换时通过不同别名来注入 */
    @Resource(name = "userServiceImpl")
    private UserService userService;

    /** 注册请求重定向到首页 index.jsp */
    @GetMapping(value = "/index")
    public String index(HttpServletResponse httpServletResponse) {
        // 重定向到前端页面 index.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.INDEX);
    }

    /** 注册请求重定向到用户注册页面 register.jsp */
    @GetMapping(value = "/register")
    public String register(HttpServletResponse response) {
        // 重定向到前端页面 register.jsp
        return response.encodeRedirectURL(UrlConstant.REGISTER);
    }

    /** 处理用户注册的请求 */
    @PostMapping(value = "/toRegister")
    public String toRegister(Model model, @ModelAttribute(value = "registerDTO") RegisterDTO registerDTO,
            HttpServletResponse httpServletResponse) {
        // 处理用户注册业务
        ResponseVO responseVO = userService.register(registerDTO);
        // 将结果放入 model 中，在模板中可以取到 model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        registerDTO.setName(registerDTO.getName());

        if (ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 重定向到前端登录页面 login.jsp，并且携带数据过去
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }

        return httpServletResponse.encodeRedirectURL(UrlConstant.REGISTER);
    }

    /** 登录请求重定向到用户登录页面 login.jsp */
    @GetMapping(value = "/login")
    public String login(HttpServletResponse httpServletResponse) {
        // 重定向到前端页面 login.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
    }

    /** 处理用户登录的请求 */
    @PostMapping(value = "/toLogin")
    public String toLogin(Model model, @ModelAttribute(value = "loginDTO") LoginDTO loginDTO,
            HttpServletResponse httpServletResponse, HttpSession httpSession) {
        // 处理用户登录业务
        ResponseVO responseVO = userService.login(loginDTO);
        // 将结果放入 model 中，在模板中可以取到 model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        // 设置登录 session，给 JSP 模版用
        httpSession.setAttribute("name", loginDTO.getName());
        httpSession.setAttribute("type", loginDTO.getType());
        // 将类型码转换为中文说明
        httpSession.setAttribute("typeName", UserTypeConstant.typeToDesc(loginDTO.getType()));
        // 组装结果返回
        if (responseVO != null && ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 登录成功重定向到前端首页 index.jsp，并且携带数据过去
            return httpServletResponse.encodeRedirectURL(UrlConstant.INDEX);
        }
        // 登录失败则跳转到前端登录页面 login.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
    }

    /** 查看用户列表 */
    @GetMapping(value = "/view")
    public String view(HttpServletResponse response) {
        // 重定向到前端 view.jsp
        return response.encodeRedirectURL("/view");
    }

    /** 处理查看用户列表的请求 */
    @GetMapping(value = "/toView")
    public String toView(Model model, HttpServletResponse httpServletResponse) {
        // 处理用户列表查看业务
        List<ViewVO> lstViewVOs = userService.view();
        model.addAttribute(CommonConstant.LST_VIEW_VOS, lstViewVOs);
        // 开始重定向前端用户列表也 view.jsp，携带数据过去了。
        return httpServletResponse.encodeRedirectURL(UrlConstant.VIEW);
    }

    /** 新增用户请求重定向到用户添加页面 */
    @GetMapping(value = "/add")
    public String addUser(HttpServletResponse httpServletResponse) {
        // 重定向到前端页面 add.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.ADD);
    }

    /** 处理新增用户的请求 */
    @PostMapping(value = "/toAdd")
    public String toAdd(Model model, @ModelAttribute(value = "addDTO") AddDTO addDTO,
            HttpServletResponse httpServletResponse) {
        // 处理新增用户业务
        ResponseVO responseVO = userService.add(addDTO);
        // 处理成功
        if (ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 处理业务
            List<ViewVO> lstViewVOs = userService.view();
            if (lstViewVOs != null) {
                model.addAttribute(CommonConstant.LST_VIEW_VOS, lstViewVOs);
                // 开始重定向，携带数据过去
                return httpServletResponse.encodeRedirectURL(UrlConstant.VIEW);
            }
        }
        // 用户添加失败，重定向到前端页面 add.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.ADD);
    }

    /** 用户修改请求重定向到用户修改页面 modify.jsp */
    @GetMapping(value = "/modify")
    public String modify(HttpServletResponse httpServletResponse) {
        // 重定向到 modify.jsp 页面
        return httpServletResponse.encodeRedirectURL(UrlConstant.MODIFY);
    }

    /** 处理用户信息修改的请求 */
    @PostMapping(value = "/toModify")
    public String toModify(Model model, @ModelAttribute(value = "modifyDTO") ModifyDTO modifyDTO,
            HttpServletResponse httpServletResponse, HttpSession httpSession) {
        // 从 session 中取得用户名字及类型
        String strName = httpSession.getAttribute("name").toString();
        String strType = httpSession.getAttribute("type").toString();
        modifyDTO.setName(strName);
        modifyDTO.setType(strType);
        // 处理用户信息修改业务
        ResponseVO responseVO = userService.modify(modifyDTO);
        // 将结果放入 model 中，在模板中可以取到 model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        if (ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 密码修改成功，从 session 中删除 user 属性，用户退出登录
            httpSession.removeAttribute("name");
            httpSession.removeAttribute("type");
            // 开始重定向前端登录页面 login.jsp，并且携带数据过去
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        // 修改失败时，继续重定向到前端用户信息修改页面 modify.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.MODIFY);
    }

    /** 删除用户 */
    @GetMapping(value = "/delete")
    public String delete(Model model, @ModelAttribute(value = "deleteDTO") DeleteDTO deleteDTO,
            HttpServletResponse httpServletResponse) {
        // 处理逻辑删除用户业务
        ResponseVO responseVO = userService.delete(deleteDTO);
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        // 查询用户列表数据
        List<ViewVO> lstViewVOs = userService.view();
        if (lstViewVOs != null) {
            model.addAttribute(CommonConstant.LST_VIEW_VOS, lstViewVOs);
        }
        // 逻辑删除用户后，跳转到用户前端列表页 view.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.VIEW);
    }

    /** 注销登录 */
    @GetMapping(value = "/loginOut")
    public String loginOut(HttpSession httpSession, HttpServletResponse httpServletResponse) {
        // 从 session 中删除 user 属性，用户退出登录
        httpSession.removeAttribute("name");
        httpSession.removeAttribute("type");
        // 开始重定向到前端页面 login.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
    }

}
