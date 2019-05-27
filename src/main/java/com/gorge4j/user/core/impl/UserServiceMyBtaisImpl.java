package com.gorge4j.user.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gorge4j.user.constant.ResponseConstant;
import com.gorge4j.user.constant.UserTypeConstant;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.dao.UserManageDemoMyBatisMapper;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.entity.UserManageDemoMyBatis;
import com.gorge4j.user.util.Md5Util;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;

/**
 * @Title: UserServiceMyBtaisImpl.java
 * @Description: 用户管理系统 MyBatis 版本实现类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:56:03
 * @version v1.0
 */

@Service("userServiceMyBatisImpl")
public class UserServiceMyBtaisImpl implements UserService {

    @Autowired
    UserManageDemoMyBatisMapper userManageDemoMyBatisMapper;

    /** 用户注册 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO register(RegisterDTO registerDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        if (checkUserDuplicate(registerDTO.getName(), UserTypeConstant.ORDINARY)) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户名重复，请重新注册！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 创建用户对象实体并进行赋值
        UserManageDemoMyBatis userManageDemoMyBatis = new UserManageDemoMyBatis();
        userManageDemoMyBatis.setName(registerDTO.getName());
        userManageDemoMyBatis.setPassword(Md5Util.md5(registerDTO.getPassword()));
        userManageDemoMyBatis.setType(UserTypeConstant.ORDINARY);
        userManageDemoMyBatis.setGmtCreate(date);
        userManageDemoMyBatis.setGmtModified(date);
        userManageDemoMyBatis.setIsDelete(false);
        // 保存数据库
        userManageDemoMyBatisMapper.insert(userManageDemoMyBatis);
        // 组装返回的结果对象
        responseVO.setCode(ResponseConstant.SUCCESS);
        responseVO.setMessage("恭喜您，注册成功！请登录");
        // 返回组装好的结果集
        return responseVO;
    }

    /** 用户登录 */
    @Override
    public ResponseVO login(LoginDTO loginDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        UserManageDemoMyBatis userManageDemoMyBatis =
                userManageDemoMyBatisMapper.findUserManageDemoByNameAndPasswordAndType(loginDTO.getName(),
                        Md5Util.md5(loginDTO.getPassword()), loginDTO.getType());
        // 如果数据库存在记录，则将记录添加到返回的结果集对象中
        if (userManageDemoMyBatis == null) {
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("登录失败，用户不存在、已删除或者用户名、密码或类型选择错误！");
            // 返回组装好的结果集
            return responseVO;
        }
        // 组装返回的结果对象
        responseVO.setCode(ResponseConstant.SUCCESS);
        responseVO.setMessage("登录成功！");
        // 返回组装好的结果集
        return responseVO;
    }

    /** 添加用户 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO add(AddDTO addDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 检查用户是否重复
        if (checkUserDuplicate(addDTO.getName(), UserTypeConstant.ORDINARY)) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户名重复，请重新添加！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 创建用户对象实体并进行赋值
        UserManageDemoMyBatis userManageDemoMyBatis = new UserManageDemoMyBatis();
        userManageDemoMyBatis.setName(addDTO.getName());
        userManageDemoMyBatis.setPassword(Md5Util.md5(addDTO.getPassword()));
        userManageDemoMyBatis.setType(UserTypeConstant.ORDINARY);
        userManageDemoMyBatis.setGmtCreate(date);
        userManageDemoMyBatis.setGmtModified(date);
        userManageDemoMyBatis.setIsDelete(false);
        // 保存数据库
        userManageDemoMyBatisMapper.insert(userManageDemoMyBatis);
        // 组装返回的结果对象
        responseVO.setCode(ResponseConstant.SUCCESS);
        responseVO.setMessage("添加成功！");
        // 返回组装好的结果
        return responseVO;
    }

    /** 查看用户列表 */
    @Override
    public List<ViewVO> view() {
        // 定义返回的结果集对象，这种定义方式是对象形式的集合
        List<ViewVO> lstViewVOs = new ArrayList<>();
        List<UserManageDemoMyBatis> lsUserManageDemoMyBatiss =
                userManageDemoMyBatisMapper.findAllByTypeAndStatus(UserTypeConstant.ORDINARY, false);
        for (UserManageDemoMyBatis userManageDemoMyBatis : lsUserManageDemoMyBatiss) {
            // 创建一个新对象来存储查询出的一条条记录
            ViewVO viewVO = new ViewVO();
            // 下边给对象附值
            viewVO.setId(userManageDemoMyBatis.getId());
            viewVO.setName(userManageDemoMyBatis.getName());
            viewVO.setType(UserTypeConstant.typeToDesc(userManageDemoMyBatis.getType()));
            viewVO.setGmtCreate(userManageDemoMyBatis.getGmtCreate());
            // 将对象放入结果集中
            lstViewVOs.add(viewVO);
        }
        // 返回组装好的结果集
        return lstViewVOs;
    }

    /** 密码修改 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO modify(ModifyDTO modifyDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 判断新密码和确认新密码是否一致
        if (modifyDTO.getNewPassword() != null && modifyDTO.getConfirmNewPassword() != null
                && !modifyDTO.getNewPassword().equals(modifyDTO.getConfirmNewPassword())) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("新密码和确认新密码不一致，请检查！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        // 判断用户密码是否正确
        UserManageDemoMyBatis userManageDemoMyBatis =
                userManageDemoMyBatisMapper.findUserManageDemoByNameAndPasswordAndType(modifyDTO.getName(),
                        Md5Util.md5(modifyDTO.getPassword()), modifyDTO.getType());
        if (userManageDemoMyBatis == null) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户原密码不正确，请检查！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 更新用户的信息
        int iUpdate = userManageDemoMyBatisMapper.updatePasswordByNameAndType(Md5Util.md5(modifyDTO.getNewPassword()),
                modifyDTO.getName(), modifyDTO.getType(), date);
        if (iUpdate != 1) {
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("密码修改失败，没有修改到或者修改了多条记录！");
            // 返回组装好的结果
            return responseVO;
        }
        responseVO.setCode(ResponseConstant.SUCCESS);
        responseVO.setMessage("密码修改成功！");
        // 返回组装好的结果
        return responseVO;
    }

    /** 删除用户 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO delete(DeleteDTO deleteDTO) {
        // 定义返回的结果对象
        ResponseVO responseVO = new ResponseVO();
        Date date = new Date();
        int iResult = userManageDemoMyBatisMapper.updateStatusById(deleteDTO.getId(), date);
        if (iResult != 1) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("删除失败，没有更新到或者更新了多条记录！");
            // 返回组装好的结果
            return responseVO;
        }
        responseVO.setCode(ResponseConstant.SUCCESS);
        responseVO.setMessage("用户删除成功！");
        // 返回组装好的结果
        return responseVO;

    }

    /**
     * 检查用户是否重复
     * 
     * @param name 名字
     * @param type 类型
     * @return
     */
    private boolean checkUserDuplicate(String name, String type) {
        boolean isDuplicate = false;
        UserManageDemoMyBatis userManageDemoMyBatis =
                userManageDemoMyBatisMapper.findUserManageDemoByNameAndType(name, type);
        // 如果数据库存在记录，则将记录添加到返回的结果集对象中
        if (userManageDemoMyBatis != null) {
            isDuplicate = true;
        }
        return isDuplicate;
    }

}
