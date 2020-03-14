package com.helix.datatrail.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//扫描mapper包下的所有mapper接口和映射文件
@MapperScan(basePackages = "com.helix.datatrail.mapper")
//添加启动类
@SpringBootApplication
public class Application {
}
