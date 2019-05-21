# 用户管理系统介绍
通过实现一个用户管理系统，从解决问题的视角，以技术演进的方式逐步集成各种技术，由浅入深，循序渐进。

### 需求背景
> 1、用户注册的功能（用户有管理员类型及普通类型，仅支持普通类型用户的注册，管理员用户在创建数据库时初始化进去）；  
> 2、用户登录的功能（支持管理员及普通用户的登录，通过类型切换，登录后显示个人信息及功能菜单）；  
> 3、用户修改个人信息（暂时仅支持密码）的功能；  
> 4、管理员查看用户列表的功能，列表模块支持逻辑删除用户的功能；  
> 5、管理员添加用户的功能（要求用户名不能重复）；  
> 6、退出登录的功能（清除登录的 session ，跳转到登录页）；  
> 7、完全遵守阿里的 P3C 编码规范；  
> 8、Findbugs 静态代码检查清零；  
> 9、SonarLint 静态代码检查清零。 

### 采用的技术栈
基础技术栈是 Spring Boot + MySQL + JSP/Thymleaf，目标是以技术演进的方式，逐步过渡到一个可以投入到实战开发的脚手架项目  

### 使用说明
1、笔者的本地数据库账号及密码为：root/12345678，如果你本地数据库的账号密码不一样，请在项目的包 com.gorge4j.user.constat 下的 SqlConstant.java 类里修改成自己的账号密码，注意密码需 base64 加密 [去加密](http://tool.oschina.net/encrypt?type=3)；  
2、在 MySQL 中创建数据库，数据库名：springboot-user-demo，字符集：utf8mb4，排序规则：utf8mb4\_general\_ci；  
3、在 MySQL 中创建表，SQL 如下：
 
```sql 
CREATE TABLE `user_manage_demo` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，用户id',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `type` varchar(5) NOT NULL COMMENT '用户类型（A：管理员；O：普通用户）',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '记录是否逻辑删除（0：未删除；1：已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_type` (`name`,`type`) USING BTREE COMMENT '用户名称、类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='用户管理表';
```

4、数据库表中初始化一条管理员数据  

```sql
INSERT INTO `springboot-user-demo`.`user_manage_demo`(`id`, `name`, `password`, `type`, `gmt_create`, `gmt_modified`, `is_delete`) VALUES (1, 'admin', '123456', 'A', '2019-05-19 20:26:22', '2019-05-19 20:26:27', 0);
```

5、将项目下载到本地，然后通过 IDE（Eclipse/IDEA） 导入项目后启动项目，启动正常后，在浏览器中输入：http://localhost:8080/login 来访问用户管理系统（管理员账号及密码：admin/123456），常用的访问地址:  
注册：http://localhost:8080/regisger   
登录：http://localhost:8080/login  
登录后的首页：http://localhost:8080/index  
