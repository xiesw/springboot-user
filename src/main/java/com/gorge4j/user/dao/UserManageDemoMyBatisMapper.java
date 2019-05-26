package com.gorge4j.user.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.gorge4j.user.entity.UserManageDemoMyBatis;

/**
 * @Title: UserManageDemoMyBatisMapper.java
 * @Description: 用户管理 MyBatis 版本持久化类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:24:15
 * @version v1.0
 */

@Mapper
public interface UserManageDemoMyBatisMapper {

    /**
     * 用户注册/新增
     * 
     * @param userManageDemoMyBatis
     * @return
     */
    int insert(UserManageDemoMyBatis userManageDemoMyBatis);


    /**
     * 通过名字、密码和类型查询用户信息
     * 
     * @param name 用户名
     * @param password 用户密码
     * @param type 用户类型
     * @return
     */
    UserManageDemoMyBatis findUserManageDemoByNameAndPasswordAndType(String name, String password, String type);

    /**
     * 通过类型、状态查询用户信息
     * 
     * @param type 用户类型
     * @param isDelete 用户状态
     * @return
     */
    List<UserManageDemoMyBatis> findAllByTypeAndStatus(String type, Boolean isDelete);

    /**
     * 通过名字和类型更新用户密码
     * 
     * @param password 用户密码
     * @param name 用户名
     * @param type 用户类型
     * @param gmtModified 更新时间
     * @return
     */
    int updatePasswordByNameAndType(String password, String name, String type, Date gmtModified);

    /**
     * 通过用户id更新用户状态
     * 
     * @param id 用户id
     * @param gmtModified 更新时间
     * @return
     */
    int updateStatusById(Integer id, Date gmtModified);

    /**
     * 通过用户名和类型查找用户
     * 
     * @param name 用户名
     * @param type 用户类型
     * @return
     */
    UserManageDemoMyBatis findUserManageDemoByNameAndType(String name, String type);

}
