package com.gorge4j.user.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gorge4j.user.constant.ResponseConstant;
import com.gorge4j.user.constant.UserTypeConstant;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.entity.UserManageDemoJdbcTemplate;
import com.gorge4j.user.util.Md5Util;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;

/**
 * @Title: UserServiceJdbcTemplateImpl.java
 * @Description: 用户管理系统 JdbcTemplate 版本实现类（常用以下三个方法: 1.execute 方法，用于直接执行
 *               SQL；2.update方法，用户新增修改删除；3.query 方法，用于查询）
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-28 21:06:09
 * @version v1.0
 */

@Service("userServiceJdbcTemplateImpl")
public class UserServiceJdbcTemplateImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** 用户注册 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO register(RegisterDTO registerDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 检查注册的用户名是否重复
        if (checkUserDuplicate(registerDTO.getName(), UserTypeConstant.ORDINARY)) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户名重复，请重新注册！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 构造插入用户注册信息的 SQL 脚本
        String strSql =
                "INSERT INTO user_manage_demo (name, password, type, gmt_create, gmt_modified, is_delete) VALUES (?, ?, ?, ?, ?, ?)";
        // 执行 SQL 脚本，插入用户记录
        int rows = jdbcTemplate.update(strSql, registerDTO.getName(), Md5Util.md5(registerDTO.getPassword()),
                UserTypeConstant.ORDINARY, date, date, false);
        if (rows <= 0) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户注册失败，请重试！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
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
        // 构造用户登录的查询 SQL
        String strSql =
                "SELECT u.* FROM user_manage_demo u WHERE u.name = ? AND u.password= ? AND u.type = ? AND u.is_delete = false";
        // 查询用户注册的信息是否正确
        UserManageDemoJdbcTemplate userManageDemoJdbcTemplate =
                jdbcTemplate.queryForObject(strSql, new UserManageDemoJdbcTemplate(), loginDTO.getName(),
                        Md5Util.md5(loginDTO.getPassword()), loginDTO.getType());
        // 如果数据库查不到记录，说明提供的登录信息不正确，则提示用户登录失败，并告知相关信息
        if (userManageDemoJdbcTemplate == null) {
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
        // 检查添加的用户是否重复
        if (checkUserDuplicate(addDTO.getName(), UserTypeConstant.ORDINARY)) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户名重复，请重新添加！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 构造添加用户的 SQL
        String strSql =
                "INSERT INTO user_manage_demo (name, password, type, gmt_create, gmt_modified, is_delete) VALUES (?, ?, ?, ?, ?, ?)";
        // 执行 SQL 脚本，添加用户
        int rows = jdbcTemplate.update(strSql, addDTO.getName(), Md5Util.md5(addDTO.getPassword()),
                UserTypeConstant.ORDINARY, date, date, false);
        // 添加用户不成功，提示用户添加失败，并告知相关信息
        if (rows <= 0) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户添加失败，请重试！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
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
        // 构造用户列表查询的 SQL
        String strSql = "SELECT u.* FROM user_manage_demo u WHERE u.type = ? AND u.is_delete = ?";
        // 查询用户列表的信息
        List<UserManageDemoJdbcTemplate> lstUserManageDemoJdbcTemplates =
                jdbcTemplate.query(strSql, new UserManageDemoJdbcTemplate(), UserTypeConstant.ORDINARY, false);
        // 查询结果迭代存入返回对象 list 中
        for (UserManageDemoJdbcTemplate userManageDemoJdbcTemplate : lstUserManageDemoJdbcTemplates) {
            // 创建一个新对象来存储查询出的一条条记录
            ViewVO viewVO = new ViewVO();
            // 下边给对象附值
            viewVO.setId(userManageDemoJdbcTemplate.getId());
            viewVO.setName(userManageDemoJdbcTemplate.getName());
            viewVO.setType(UserTypeConstant.typeToDesc(userManageDemoJdbcTemplate.getType()));
            viewVO.setGmtCreate(userManageDemoJdbcTemplate.getGmtCreate());
            // 将对象放入结果集中
            lstViewVOs.add(viewVO);
        }
        // 返回组装好的结果集
        return lstViewVOs;
    }

    /** 用户信息修改 */
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
        // 校验用户密码是否正确，防止用户万一在登录态页面没退出的情况，密码被其他人篡改
        String strSql1 =
                "SELECT u.* FROM user_manage_demo u WHERE u.name = ? AND u.password= ? AND u.type = ? AND is_delete = false";
        List<UserManageDemoJdbcTemplate> lstUserManageDemoJdbcTemplates =
                jdbcTemplate.query(strSql1, new UserManageDemoJdbcTemplate(), modifyDTO.getName(),
                        Md5Util.md5(modifyDTO.getPassword()), modifyDTO.getType());
        // 校验用户原密码不正确，提醒操作的用户
        if (CollectionUtils.isEmpty(lstUserManageDemoJdbcTemplates)) {
            responseVO.setCode(ResponseConstant.FAIL);
            responseVO.setMessage("用户原密码不正确，请检查！");
            // 返回组装的提示信息对象给前端页面
            return responseVO;
        }
        Date date = new Date();
        // 构造更新用户密码 SQL
        String strSql2 =
                "UPDATE user_manage_demo u SET u.password = ?, u.gmt_modified = ? WHERE u.name = ? AND u.type = ?";
        // 执行更新用户密码的 SQL
        int iUpdate = jdbcTemplate.update(strSql2, Md5Util.md5(modifyDTO.getNewPassword()), date, modifyDTO.getName(),
                modifyDTO.getType());
        // 更新用户密码失败，告知用户，并提示相关信息
        if (iUpdate != 1) {
            // 组装返回的结果
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
        // 构造并执行逻辑删除用户的 SQL
        int iResult = jdbcTemplate.update(
                "UPDATE user_manage_demo u SET u.is_delete = true, u.gmt_modified = ? WHERE u.id =  ?", date,
                deleteDTO.getId());
        // 逻辑删除用户失败，告知操作用户，并返回相关提示信息
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
        // 构造检查用户是否重复的 SQL
        String strSql = "SELECT u.* FROM user_manage_demo u WHERE u.name = ? AND u.type = ?";
        // 根据用户名及类型查询数据库中记录是否存在
        List<UserManageDemoJdbcTemplate> lstUserManageDemoJdbcTemplates =
                jdbcTemplate.query(strSql, new UserManageDemoJdbcTemplate(), name, type);
        // 如果数据库存在记录，则将记录添加到返回的结果集对象中
        if (CollectionUtils.isNotEmpty(lstUserManageDemoJdbcTemplates)) {
            isDuplicate = true;
        }
        return isDuplicate;
    }

}
