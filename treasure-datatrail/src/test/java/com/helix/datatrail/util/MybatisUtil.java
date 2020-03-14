package com.helix.datatrail.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lijianyu
 * @date 2018/12/29 16:25
 */
public class MybatisUtil {
    /*
     * 定义配置文件的位置
     */
    private static final String CONFIG_PATH = "db-mybatis-config.xml";

    private static SqlSessionFactory sqlSessionFactory = null;

    static{
        if(sqlSessionFactory == null){
            InputStream stream = null;
            try {
                stream = Resources.getResourceAsStream(CONFIG_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 可以根据配置的相应环境读取相应的数据库环境
            // SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream, "development");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
        }
    }

    /*
     * 获取数据库访问链接
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /*
     * 获取数据库访问链接
     */
    public static SqlSession getSqlSession() {
        SqlSession session = null;
        session = sqlSessionFactory.openSession();
        return session;
    }

    /*
     * 获取数据库访问链接
     */
    public static void closeSession(SqlSession session) {
        session.close();
    }

}
