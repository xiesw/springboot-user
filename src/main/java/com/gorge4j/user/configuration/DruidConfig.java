package com.gorge4j.user.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Title: DruidConfig.java
 * @Description: Druid 数据源相关配置的注入。数据源 SpringBoot 会自动注入 DruidDataSource，但是 Druid 的其它相关的参数的绑定需要手动操作绑定
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-05-28 22:34:17
 * @version v1.0
 */

@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

}
