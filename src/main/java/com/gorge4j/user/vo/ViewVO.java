package com.gorge4j.user.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: ViewVO.java
 * @Description: 用户列表查看值对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

public class ViewVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 用户id */
    private Integer id;
    /** 用户名 */
    private String name;
    /** 用户类型 */
    private String type;
    /** 用户创建时间 */
    private Date gmtCreate;

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

    /** 重写POJO类的toString方法，方便在输出对象时能看到具体的参数信息，而不是一串无业务意义的字符 */
    @Override
    public String toString() {
        return "ViewVO [id=" + id + ", name=" + name + ", type=" + type + ", gmtCreate=" + gmtCreate + "]";
    }

}
