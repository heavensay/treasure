package com.helix.datatrail.user;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.UserMapper;
import com.helix.datatrail.mapper.util.DataTrailDBUtils;
import com.helix.datatrail.mapper.util.MybatisUtil;
import com.helix.datatrail.mapper.util.ThreadLocalSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * @author lijianyu
 * @date 2018/12/29 16:24
 */
public class UserTest {

    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById(1L);
        MybatisUtil.closeSession(sqlSession);
    }

    @Test
    public void createUser(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ThreadLocalSqlSession.put(sqlSession);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("Tom");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = userMapper.createUser(user);
        System.out.println(result);
        sqlSession.commit();
        MybatisUtil.closeSession(sqlSession);

        ThreadLocalSqlSession.clear();
    }

    @Test
    public void queryNewestSnapshotByTime() {
        User user = DataTrailDBUtils.queryNewestSnapshotByTime(User.class,3L,new Date());
        Assert.assertNotNull(user);
    }

}
