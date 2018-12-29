package com.helix.datatrail;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.UserMapper;
import com.helix.datatrail.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * @author lijianyu
 * @date 2018/12/29 16:24
 */
public class MybatisTest {

    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
//        Mapper test = sqlSession.getMapper(Mapper.class);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        System.out.println(test.countByExample(new Example()));
        User user = userMapper.getUserById(1L);
        MybatisUtil.closeSession(sqlSession);
    }

    @Test
    public void createUser(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("Tom");
        user.setAge(new Random(100).nextInt());
        user.setCreateDate(new Date());
        int result = userMapper.createUser(user);
        System.out.println(result);
        MybatisUtil.closeSession(sqlSession);
    }

}
