package com.helix.datatrail;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.user.UserMapper;
import com.helix.datatrail.util.DataTrailSqlSessionManager;
import com.helix.datatrail.util.MybatisUtil;
import com.helix.datatrail.util.ThreadLocalSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * 在没有spring环境下，DataTrail组件测试
 */
public class MybatisWithoutSpringTest {

    /**
     * 创建user，记录user历史快照；
     */
    @Test
    public void triggerDataTrail() {
        try {
            SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
            DataTrailSqlSessionManager.initDataTrailConfig(sqlSessionFactory);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            ThreadLocalSqlSession.put(sqlSession);
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            User user = new User();
            user.setName("cherry");
            user.setAge(new Random().nextInt(100));
            user.setCreateDate(new Date());

            int result = mapper.createUser(user);
            sqlSession.commit();
            System.out.println(result);
        }finally {
            ThreadLocalSqlSession.clear();
        }

    }

    /**
     * 事务为自动提交
     * 创建user，记录user历史快照；
     */
    @Test
    public void autoCommitDataTrail() throws Exception{
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        DataTrailSqlSessionManager.initDataTrailConfig(sqlSessionFactory);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.getConnection().setAutoCommit(true);
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
