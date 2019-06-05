package com.gorge4j.user.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

/**
 * @Title: UserManageDemoJpa.java
 * @Description: 用户管理表 JPA 版本实体类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-24 21:02:59
 * @version v1.0
 */

@Data
@Entity
@Table(name = "user_manage_demo")
public class UserManageDemoJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
