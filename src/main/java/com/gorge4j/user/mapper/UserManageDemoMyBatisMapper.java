package com.gorge4j.user.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.gorge4j.user.entity.UserManageDemoMyBatis;
import org.apache.ibatis.annotations.Param;

/**
 * @Title: UserManageDemoMyBatisMapper.java
 * @Description: 用户管理系统 MyBatis 版本持久化类
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
    UserManageDemoMyBatis findUserManageDemoByNameAndPasswordAndType(@Param("name") String name, @Param("password") String password,@Param("type") String type);

    /**
     * 通过类型、状态查询用户信息
     *
     * @param type 用户类型
     * @param isDelete 用户状态
     * @return
     */
    List<UserManageDemoMyBatis> findAllByTypeAndStatus(@Param("type") String type,@Param("isDelete") Boolean isDelete);

    /**
     * 通过名字和类型更新用户密码
     *
     * @param password 用户密码
     * @param name 用户名
     * @param type 用户类型
     * @param gmtModified 更新时间
     * @return
     */
    int updatePasswordByNameAndType(@Param("password") String password,@Param("name") String name,@Param("type") String type,@Param("gmtModified") Date gmtModified);

    /**
     * 更新用户信息
     *
     * @param userManageDemoMyBatis
     * @return
     */
    int update(UserManageDemoMyBatis userManageDemoMyBatis);

    /**
     * 通过用户id更新用户状态
     *
     * @param id 用户id
     * @param gmtModified 更新时间
     * @return
     */
    int updateStatusById(@Param("id") Integer id,@Param("gmtModified") Date gmtModified);

    /**
     * 通过用户名和类型查找用户
     *
     * @param name 用户名
     * @param type 用户类型
     * @return
     */
    UserManageDemoMyBatis findUserManageDemoByNameAndType(@Param("name") String name, @Param("type") String type);

}
