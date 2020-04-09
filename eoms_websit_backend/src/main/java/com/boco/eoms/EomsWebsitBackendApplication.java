package com.boco.eoms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement  // 启动注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(value = "com.boco.eoms.websit.*.mapper")
public class EomsWebsitBackendApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EomsWebsitBackendApplication.class, args);
    }

}
