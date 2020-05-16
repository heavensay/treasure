package com.helix.datatrail.spring;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.user.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * spring结合mybatis环境下，使用DataTrail组件测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/helix/datatrail/spring/spring-mybatis.xml"})
public class SpringContextTest2 {

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建用户，生成快照数据；
     * 事务各管各的
     */
    @Test
    public void createUser(){
        User user = new User();
        user.setName("cherry");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = userMapper.createUser(user);
        System.out.println(result);
    }
}
