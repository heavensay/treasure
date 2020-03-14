package com.helix.datatrail.util;

import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author lijianyu
 * @date 2019/1/2 17:50
 */
public class ThreadLocalSqlSession {

//    private static SqlSession sqlSession = null;
    private static final ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<SqlSession>();

    public static SqlSession get() {
        return sqlSessionThreadLocal.get();
    }

    public static void put(SqlSession sqlSession){
//        ThreadLocalSqlSession.sqlSession = sqlSession;
        sqlSessionThreadLocal.set(sqlSession);
    }


    public static void clear(){
//        sqlSession = null;
        sqlSessionThreadLocal.remove();
    }
}
