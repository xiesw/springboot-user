package com.gorge4j.user.core;

import java.util.List;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;

/**
 * @Title: UserService.java
 * @Description: 用户管理系统接口类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-12 20:50:35
 * @version v1.0
 */

public interface UserService {

    /**
     * 用户注册
     * 
     * @param registerDTO 用户注册数据传输对象
     * @return
     */
    ResponseVO register(RegisterDTO registerDTO);

    /**
     * 用户登录
     * 
     * @param loginDTO 用户登录数据传输对象
     * @return
     */
    ResponseVO login(LoginDTO loginDTO);

    /**
     * 添加用户
     * 
     * @param addDTO 用户添加数据传输对象
     * @return
     */
    ResponseVO add(AddDTO addDTO);

    /**
     * 查看用户列表
     * 
     * @return
     */
    List<ViewVO> view();

    /**
     * 用户修改
     * 
     * @param modifyDTO 用户信息修改数据传输对象
     * @return
     */
    ResponseVO modify(ModifyDTO modifyDTO);

    /**
     * 用户登录
     * 
     * @param deleteDTO 用户删除传输对象
     * @return
     */
    ResponseVO delete(DeleteDTO deleteDTO);

}
