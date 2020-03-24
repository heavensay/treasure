package com.helix.datatrail.util;

import com.helix.datatrail.exception.DataTrailException;
import com.helix.datatrail.interceptor.DataTrailInterceptor;
import com.helix.datatrail.mapper.DataTrailMapper;
import com.helix.datatrail.spring.SpringMybatisContextHolder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Mybatis SqlSession工具类；
 * SqlSession事务由外部项目控制，组件不做事务控制；
 * 在spring事务管理框架下，@transactional标识事务
 * @author lijianyu
 */
public class DataTrailSqlSessionManager {

    private static Logger logger = LoggerFactory.getLogger(DataTrailSqlSessionManager.class);

    private static SqlSessionFactory sqlSessionFactory = null;

    /*
     * 获取数据库访问链接
     */
    public static SqlSessionFactory obtainSqlSessionFactory() {
        if(DataTrailSqlSessionManager.sqlSessionFactory != null){
            return DataTrailSqlSessionManager.sqlSessionFactory;
        }

        //2 是spring项目，从springfactory中获取；
        if(isSpringMybatisEnv()){
            DataTrailSqlSessionManager.sqlSessionFactory = SpringMybatisContextHolder.obtainSqlSessionFactory();
            return DataTrailSqlSessionManager.sqlSessionFactory;
        }else{
            logger.info("非spring环境或spring ApplicationContext没有注入到组件中");
        }

        //1 是springboot项目，优先从配置项中获取；


        //2 是spring项目，从springfactory中获取；
        if(SpringMybatisContextHolder.obtainSqlSessionFactory() != null){
            return SpringMybatisContextHolder.obtainSqlSessionFactory();
        }else{
            logger.info("非spring环境或spring ApplicationContext没有注入到组件中");
        }

        return sqlSessionFactory;
    }

    /*
     * 获取数据库访问链接
     */
    public static SqlSession obtainSqlSession() {
        SqlSession sqlSession = null;
//        if(DataTrailDBConfig.sqlSessionFactory != null){
//            return DataTrailDBConfig.sqlSessionFactory;
//        }

        //1 是springboot项目，优先从配置项中获取；


        //2 是spring项目，从springfactory中获取；
        if(isSpringMybatisEnv()){
            logger.debug("support spring mybatis");
            sqlSession = SqlSessionUtils.getSqlSession(obtainSqlSessionFactory());
            if(SqlSessionUtils.isSqlSessionTransactional(sqlSession, SpringMybatisContextHolder.obtainSqlSessionFactory())){
                logger.debug("获取SqlSession,SqlSession事务受spring框架管理");
                return sqlSession;
            }else{
                logger.info("获取SqlSession,SqlSession事务没有受spring框架管理");
            }
            return sqlSession;
        }else{
            //非spring环境；SqlSession需要手工填入
            sqlSession = ThreadLocalSqlSession.get();
            if(sqlSession != null){
                return sqlSession;
            }
        }

        throw new DataTrailException("SqlSession获取失败，请检查配置");
    }

    /**
     * 初始化Datatrail需要的环境配置
     * @param sqlSessionFactory
     */
    public static void initDataTrailConfig(SqlSessionFactory sqlSessionFactory){
        if(DataTrailSqlSessionManager.sqlSessionFactory == null){
            logger.debug("init SqlSessionFactory,register DataTrailMapper,DataTrailInterceptor");
            DataTrailSqlSessionManager.sqlSessionFactory = sqlSessionFactory;
            DataTrailSqlSessionManager.sqlSessionFactory.getConfiguration().addMapper(DataTrailMapper.class);
            DataTrailSqlSessionManager.sqlSessionFactory.getConfiguration().addInterceptor(new DataTrailInterceptor());
        }else{
            logger.warn("SqlSessionFactory only set once");
        }
    }

    /**
     * 检测是否是spring和mybatis环境
     * @return
     */
    public static boolean isSpringMybatisEnv(){
        if(SpringMybatisContextHolder.isInjectApplicationContext() && SpringMybatisContextHolder.obtainSqlSessionFactory() != null){
            try {
                Class.forName("org.mybatis.spring.SqlSessionFactoryBean");
                Object bean = SpringMybatisContextHolder.getBean(SqlSessionFactoryBean.class);
                return bean != null;
            } catch (ClassNotFoundException e) {
                logger.debug("org.mybatis.spring.SqlSessionFactoryBean is not found，非spring和mybatis环境");
                return false;
            }
        }else{
            return false;
        }
    }

}
