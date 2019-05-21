package com.gorge4j.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.gorge4j.user.constant.ResponseConstant;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;

/**
 * @Title: SpringbootUserApplicationTest.java
 * @Description: 测试服务处理类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-12 18:23:40
 * @version v1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootUserApplicationTest {

    /** 自动注入userService，用来处理业务，按实现类的别名注入具体的实现 */
    @Resource(name = "userServiceImpl")
    private UserService userService;

    /** 测试注册接口 */
    @Test
    public void testRegister() {
        // 构造注册的测试数据
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("zhangsan1");
        registerDTO.setPassword("123456");
        // 处理业务
        ResponseVO responseVO = userService.register(registerDTO);
        // 测试断言
        assertEquals(responseVO.getMessage(), ResponseConstant.SUCCESS, responseVO.getCode());
    }

    /** 测试登录接口 */
    @Test
    public void testLogin() {
        // 构造登录的测试数据
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName("zhangsan");
        loginDTO.setPassword("123456");
        loginDTO.setType("O");
        // 处理业务
        ResponseVO responseVO = userService.login(loginDTO);
        // 测试断言
        assertEquals(responseVO.getMessage(), ResponseConstant.SUCCESS, responseVO.getCode());
    }

    /** 测试添加用户接口 */
    @Test
    public void testAdd() {
        // 构造添加用户的测试数据
        AddDTO addDTO = new AddDTO();
        addDTO.setName("lisi");
        addDTO.setPassword("123456");
        // 处理业务
        ResponseVO responseVO = userService.add(addDTO);
        // 测试断言
        assertEquals(responseVO.getMessage(), ResponseConstant.SUCCESS, responseVO.getCode());
    }

    /** 测试查看用户列表的接口 */
    @Test
    public void testView() {
        // 处理业务，此接口无请求参数
        List<ViewVO> lstViewVOs = userService.view();
        // 测试断言
        assertNotEquals(0, lstViewVOs.size());
    }

    /** 测试用户信息修改接口 */
    @Test
    public void testModify() {
        // 构造修改密码的测试数据
        ModifyDTO modifyDTO = new ModifyDTO();
        modifyDTO.setName("zhangsan");
        modifyDTO.setType("O");
        modifyDTO.setPassword("123456");
        modifyDTO.setNewPassword("654321");
        modifyDTO.setConfirmNewPassword("654321");
        // 处理业务
        ResponseVO responseVO = userService.modify(modifyDTO);
        // 测试断言
        assertEquals(responseVO.getMessage(), ResponseConstant.SUCCESS, responseVO.getCode());
    }

    /** 测试删除用户接口 */
    @Test
    public void testDelete() {
        // 构造删除用户的测试数据
        DeleteDTO deleteDTO = new DeleteDTO();
        deleteDTO.setId(2);
        // 处理业务
        ResponseVO responseVO = userService.delete(deleteDTO);
        // 测试断言
        assertEquals(responseVO.getMessage(), ResponseConstant.SUCCESS, responseVO.getCode());
    }

}
