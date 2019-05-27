package com.gorge4j.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Title: SpringbootUserApplication.java
 * @Description: 用户管理系统服务启动类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-12 21:23:40
 * @version v1.0
 */

@ServletComponentScan(value = "com.gorge4j.user.filter")
@SpringBootApplication
public class SpringbootUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUserApplication.class, args);
    }

}
