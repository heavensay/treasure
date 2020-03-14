package com.helix.datatrail;

import com.alibaba.fastjson.JSON;
import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.DataTrailMapper;
import com.helix.datatrail.mapper.user.UserMapper;
import com.helix.datatrail.util.DataTrailSqlSessionManager;
import com.helix.datatrail.util.MybatisUtil;
import com.helix.datatrail.util.ThreadLocalSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * spring结合mybatis环境下，使用DataTrail组件测试
 */
public class MybatisWithoutSpringTest {

    @Test
    public void triggerDataTrail() {
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        DataTrailSqlSessionManager.initSqlSessionFactory(sqlSessionFactory);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        ThreadLocalSqlSession.put(sqlSession);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setName("cherry");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = mapper.createUser(user);
        sqlSession.commit();
        ThreadLocalSqlSession.clear();

        System.out.println(result);
    }
}
