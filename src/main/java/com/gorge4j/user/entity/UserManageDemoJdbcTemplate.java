package com.gorge4j.user.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;

/**
 * @Title: UserManageDemo.java
 * @Description: 用户管理表 JdbcTemplate 版本实体类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:02:59
 * @version v1.0
 */

public class UserManageDemoJdbcTemplate implements RowMapper<UserManageDemoJdbcTemplate> {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "UserDO [id=" + id + ", name=" + name + ", password=" + password + ", type=" + type + ", gmtCreate="
                + gmtCreate + ", gmtModified=" + gmtModified + ", isDelete=" + isDelete + "]";
    }

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
