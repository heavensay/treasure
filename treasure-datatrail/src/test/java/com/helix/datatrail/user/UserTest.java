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
//        Mapper test = sqlSession.getMapper(Mapper.class);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        System.out.println(test.countByExample(new Example()));
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
    public void queryHistoryDataTrail() {
        User user = DataTrailDBUtils.queryLastHistoryDataTrailByMapperXML(1L,null,new Date(), User.class);
        Assert.assertNotNull(user);
    }

    @Test
    public void queryHistoryDataTrail2q() {
        User user = DataTrailDBUtils.querySnapshotByTime("t_ops_history",1L,null,new Date(), User.class);
        Assert.assertNotNull(user);
    }

}
