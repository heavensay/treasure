package com.helix.datatrail.springboot;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.user.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;

//添加启动类
@SpringBootApplication(scanBasePackageClasses = {Application.class})
@Controller
@RequestMapping("/")
//扫描mapper包下的所有mapper接口和映射文件
@MapperScan(basePackages = "com.helix.datatrail.mapper.user")
@PropertySource(value = "classpath:application.yml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/createUser")
    @ResponseBody
    @Transactional
    public int createUser(){
        User user = new User();
        user.setName("cherry");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = userMapper.createUser(user);
        return result;
    }

}
