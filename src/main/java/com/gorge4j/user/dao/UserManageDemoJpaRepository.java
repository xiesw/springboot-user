package com.gorge4j.user.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gorge4j.user.entity.UserManageDemoJpa;

/**
 * @Title: UserManageDemoJpaRepository.java
 * @Description: 用户管理表 JPA 版本持久化类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:24:15
 * @version v1.0
 */

@Repository
public interface UserManageDemoJpaRepository extends JpaRepository<UserManageDemoJpa, Integer> {

    /**
     * 通过名字、密码和类型查询用户信息
     * 
     * @param name 用户名
     * @param password 用户密码
     * @param type 用户类型
     * @return
     */
    @Query(value = "SELECT u FROM UserManageDemoJpa u WHERE u.name = :name AND u.password = :password AND u.type = :type")
    UserManageDemoJpa findUserManageDemoByNameAndPasswordAndType(@Param("name") String name,
            @Param("password") String password, @Param("type") String type);

    /**
     * 通过名字、类型查询用户信息
     * 
     * @param name 用户名
     * @param type 用户类型
     * @return
     */
    @Query(value = "SELECT u FROM UserManageDemoJpa u WHERE u.name = :name AND u.type = :type")
    UserManageDemoJpa findUserManageDemoByNameAndType(@Param("name") String name, @Param("type") String type);

    /**
     * 通过类型、状态查询用户信息
     * 
     * @param type 类型
     * @param isDelete 用户状态
     * @return
     */
    @Query(value = "SELECT u FROM UserManageDemoJpa u WHERE u.type = :type AND u.isDelete = :isDelete")
    List<UserManageDemoJpa> findAllByTypeAndStatus(@Param("type") String type, @Param("isDelete") Boolean isDelete);

    /**
     * 通过名字和类型更新用户信息
     * 
     * @param password 用户密码
     * @param name 用户名
     * @param type 用户类型
     * @return
     */
    @Modifying
    @Query(value = "UPDATE UserManageDemoJpa u SET u.password = :password, u.gmtModified = :gmtModified WHERE u.name = :name AND u.type = :type")
    int updatePasswordByNameAndType(@Param("password") String password, @Param("name") String name,
            @Param("type") String type, @Param("gmtModified") Date gmtModified);

    /**
     * 通过id逻辑删除用户
     * 
     * @param id 用户id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE UserManageDemoJpa u SET u.isDelete = true, u.gmtModified = :gmtModified WHERE u.id = :id")
    int updateStatusById(@Param("id") Integer id, @Param("gmtModified") Date gmtModified);

}
