package com.boco.eoms.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author ssh
 * @description:连接池配置
 * @date 2019/12/611:35
 */
public class DruidDataSource {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }
}
