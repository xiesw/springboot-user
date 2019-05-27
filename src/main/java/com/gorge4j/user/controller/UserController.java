package com.gorge4j.user.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gorge4j.user.util.ImageCodeUtil;
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

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    /** 注入 UserService，用来处理业务，按需要注入具体的实现，存在多个实现时，可以通过实现类的不同别名来切换注入 */
    @Resource(name = "userServiceMyBatisImpl")
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
    public String toRegister(Model model, @Valid @ModelAttribute(value = "registerDTO") RegisterDTO registerDTO,
            HttpServletResponse httpServletResponse) {
        // 处理用户注册业务
        ResponseVO responseVO = userService.register(registerDTO);
        // 将结果放入 model 中，在模板中可以取到 model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        registerDTO.setName(registerDTO.getName());
        if (ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 注册成功，重定向到前端登录页面 login.jsp，并且携带数据过去
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        // 注册失败，跳转到注册页面，并将失败的信息返回
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
    public String toLogin(Model model, @Valid @ModelAttribute(value = "loginDTO") LoginDTO loginDTO,
            HttpServletResponse httpServletResponse, HttpSession httpSession) {
        ResponseVO responseVO = new ResponseVO();
        // 获取前端用户输入的验证码
        String checkCode = loginDTO.getCheckCode();
        // 从 session 中获取到上一次生成并写入到 session 中的验证码，以便跟用户输入的验证码进行对比
        Object cko = httpSession.getAttribute(CommonConstant.SIMPLE_CAPTCHA);
        if (cko == null) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("请填写验证码");
            model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        String captcha = cko.toString();
        // 校验用户输入的验证码跟系统生成的验证码是否一致
        Date now = new Date();
        Long codeTime = Long.valueOf(httpSession.getAttribute(CommonConstant.CODE_TIME) + "");
        if (StringUtils.isBlank(checkCode) || StringUtils.isBlank(captcha) || !(checkCode.equalsIgnoreCase(captcha))) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("验证码不一致");
            model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        // 验证码有效时长为 5 分钟
        else if ((now.getTime() - codeTime) / CommonConstant.MILLISECOND_CONVERSION
                / CommonConstant.MINUTE_CONVERSION > CommonConstant.CAPTCHA_EXPIRY_TIME) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("验证码已过期");
            model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        // 处理用户登录业务
        responseVO = userService.login(loginDTO);
        // 将结果放入 model 中，在模板中可以取到 model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        // 设置登录 session，给 JSP 模版用
        httpSession.setAttribute(CommonConstant.USER_NAME, loginDTO.getName());
        httpSession.setAttribute(CommonConstant.USER_TYPE, loginDTO.getType());
        // 将类型码转换为中文说明
        httpSession.setAttribute(CommonConstant.USER_TYPE_NAME, UserTypeConstant.typeToDesc(loginDTO.getType()));
        // 组装结果返回
        if (responseVO == null || !ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 登录失败则跳转到前端登录页面 login.jsp
            return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
        }
        // 登录成功重定向到前端首页 index.jsp，并且携带数据过去
        return httpServletResponse.encodeRedirectURL(UrlConstant.INDEX);
    }

    /** 查看用户列表 */
    @GetMapping(value = "/view")
    public String view(HttpServletResponse response) {
        // 重定向到前端 view.jsp
        return response.encodeRedirectURL(UrlConstant.VIEW);
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
    public String toAdd(Model model, @Valid @ModelAttribute(value = "addDTO") AddDTO addDTO,
            HttpServletResponse httpServletResponse) {
        // 处理新增用户业务
        ResponseVO responseVO = userService.add(addDTO);
        // 将处理结果写入到返回对象中
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        // 用户添加失败，重定向到前端页面 add.jsp
        if (responseVO == null || !ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            return httpServletResponse.encodeRedirectURL(UrlConstant.ADD);
        }
        // 处理成功
        List<ViewVO> lstViewVOs = userService.view();
        model.addAttribute(CommonConstant.LST_VIEW_VOS, lstViewVOs);
        // 开始重定向，携带数据过去
        return httpServletResponse.encodeRedirectURL(UrlConstant.VIEW);
    }

    /** 用户修改请求重定向到用户修改页面 modify.jsp */
    @GetMapping(value = "/modify")
    public String modify(HttpServletResponse httpServletResponse) {
        // 重定向到 modify.jsp 页面
        return httpServletResponse.encodeRedirectURL(UrlConstant.MODIFY);
    }

    /** 处理用户信息修改的请求 */
    @PostMapping(value = "/toModify")
    public String toModify(Model model, @Valid @ModelAttribute(value = "modifyDTO") ModifyDTO modifyDTO,
            HttpServletResponse httpServletResponse, HttpSession httpSession) {
        // 从 Session 中取得用户名字及类型
        String strName = httpSession.getAttribute(CommonConstant.USER_NAME).toString();
        String strType = httpSession.getAttribute(CommonConstant.USER_TYPE).toString();
        modifyDTO.setName(strName);
        modifyDTO.setType(strType);
        // 处理用户信息修改业务
        ResponseVO responseVO = userService.modify(modifyDTO);
        // 将结果放入 Model 中，在模板中可以取到 Model 中的值
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        if (responseVO == null || !ResponseConstant.SUCCESS.equals(responseVO.getCode())) {
            // 修改失败时，继续重定向到前端用户信息修改页面 modify.jsp
            return httpServletResponse.encodeRedirectURL(UrlConstant.MODIFY);
        }
        // 密码修改成功，从 Session 中删除 user 属性，用户退出登录
        httpSession.removeAttribute(CommonConstant.USER_NAME);
        httpSession.removeAttribute(CommonConstant.USER_TYPE);
        // 开始重定向前端登录页面 login.jsp，并且携带数据过去
        return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
    }

    /** 删除用户 */
    @GetMapping(value = "/delete")
    public String delete(Model model, @Valid @ModelAttribute(value = "deleteDTO") DeleteDTO deleteDTO,
            HttpServletResponse httpServletResponse) {
        // 处理逻辑删除用户业务
        ResponseVO responseVO = userService.delete(deleteDTO);
        // 这里就是交互的一个重要地方，我们可以在模板中通过这些属性值访问到数据
        model.addAttribute(CommonConstant.RESPONSE_VO, responseVO);
        // 删除成功后再查询用户列表数据，返回删除后的最新用户列表数据
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
        // 从 Session 中删除 user 属性，用户退出登录
        httpSession.removeAttribute(CommonConstant.USER_NAME);
        httpSession.removeAttribute(CommonConstant.USER_TYPE);
        // 开始重定向到前端页面 login.jsp
        return httpServletResponse.encodeRedirectURL(UrlConstant.LOGIN);
    }

    /**
     * 生成图形验证码
     * 
     * @throws NoSuchAlgorithmException
     */
    @GetMapping(value = "/imageCode")
    public String imagecode(HttpServletRequest request, HttpServletResponse response)
            throws IOException, NoSuchAlgorithmException {
        OutputStream os = response.getOutputStream();
        // 生成验证码
        Map<String, Object> map = ImageCodeUtil.getImageCode(60, 20, 4);
        // 放入客户端的 Session 对象中
        request.getSession().setAttribute(CommonConstant.SIMPLE_CAPTCHA,
                map.get(CommonConstant.CAPTCHA_VALUE).toString().toLowerCase());
        // 设置验证码的生成时间，并放入客户端 Session 对象中
        request.getSession().setAttribute(CommonConstant.CODE_TIME, System.currentTimeMillis());
        // 将生成的验证码图片写到图片输出流
        try {
            ImageIO.write((BufferedImage) map.get(CommonConstant.IMAGE), CommonConstant.JPEG, os);
        } catch (IOException e) {
            log.error("图形验证码生成异常");
        }
        return null;
    }

}
