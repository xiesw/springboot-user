package com.gorge4j.user.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;
import lombok.Data;

/**
 * @Title: UserManageDemoJdbcTemplate.java
 * @Description: 用户管理表 JdbcTemplate 版本实体类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:02:59
 * @version v1.0
 */

@Data
public class UserManageDemoJdbcTemplate implements RowMapper<UserManageDemoJdbcTemplate>, Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户 id */
    private Integer id;
    /** 用户名 */
    private String name;
    /** 用户密码 */
    private String password;
    /** 用户类型 */
    private String type;
    /** 记录创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    /** 记录修改时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
    /** 记录是否删除 */
    private Boolean isDelete;

    /**
     * 用于将结果集每行数据转换为需要的类型
     */
    @Override
    public UserManageDemoJdbcTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserManageDemoJdbcTemplate userManageDemoJdbcTemplate = new UserManageDemoJdbcTemplate();
        userManageDemoJdbcTemplate.setId(rs.getInt("id"));
        userManageDemoJdbcTemplate.setName(rs.getString("name"));
        userManageDemoJdbcTemplate.setPassword(rs.getString("password"));
        userManageDemoJdbcTemplate.setType(rs.getString("type"));
        userManageDemoJdbcTemplate.setGmtCreate(rs.getDate("gmt_create"));
        userManageDemoJdbcTemplate.setGmtModified(rs.getDate("gmt_modified"));
        userManageDemoJdbcTemplate.setIsDelete(rs.getBoolean("is_delete"));
        return userManageDemoJdbcTemplate;
    }

}
