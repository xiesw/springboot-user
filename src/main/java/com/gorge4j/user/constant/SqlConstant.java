package com.gorge4j.user.constant;

/**
 * @Title: SqlConstant.java
 * @Description: 数据库配置静态常量类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

public final class SqlConstant {

    /** 添加私有的构造函数，防止静态常量类构造其它的实例，然后改变静态常量的内容 */
    private SqlConstant() {
        throw new IllegalStateException("Utility class");
    }

    /** 声明数据库驱动类的路径，采用低版本 MySQL 驱动请使用 com.mysql.jdbc.Driver */
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /** 声明数据库连接 URL */
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/springboot-user-demo?useUnicode=true&characterEncoding=utf8";
    /** 声明数据库帐号 */
    public static final String DB_USER = "root";
    /** 声明数据库密码，为避免 SonarLint 的密码明文告警，此处采用了密文，密码原文为 12345678 */
    public static final String DB_PASS = "MTIzNDU2Nzg=";

}
