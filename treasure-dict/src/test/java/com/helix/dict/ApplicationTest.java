package com.helix.dict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot测试环境的启动测试配置类
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.helix.*")
public class ApplicationTest {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class);
    }
    
}