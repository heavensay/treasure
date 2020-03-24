package com.helix.datatrail.springboot;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.user.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;

//扫描mapper包下的所有mapper接口和映射文件
//添加启动类
//@SpringBootApplication(scanBasePackages = {"com.helix.datatrail"})
@SpringBootApplication(scanBasePackageClasses = {Application.class})
@Controller
@RequestMapping("/")
//@ComponentScan(basePackages = "com.helix.datatrail.*")
//@Configuration
@MapperScan(basePackages = "com.helix.datatrail.mapper.user")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/createUser")
    @ResponseBody
    public int createUser(){
        User user = new User();
        user.setName("cherry");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = userMapper.createUser(user);
        return result;
    }



}
