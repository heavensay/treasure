package com.helix.datatrail;

import com.helix.datatrail.interceptor.DataTrailInterceptor;
import com.helix.datatrail.mapper.DataTrailMapper;
import com.helix.datatrail.util.DataTrailSqlSessionManager;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-03-24 22:00
 */
public class DataTrailConfig {

    private static SqlSessionFactory sqlSessionFactory = null;
    
    /**
     * 初始化Datatrail需要的环境配置
     * @param sqlSessionFactory
     */
    public static void registerDataTrailConfig(SqlSessionFactory sqlSessionFactory){
        if(DataTrailSqlSessionManager.sqlSessionFactory == null){
            logger.debug("init SqlSessionFactory,register DataTrailMapper,DataTrailInterceptor");
            DataTrailSqlSessionManager.sqlSessionFactory = sqlSessionFactory;
            DataTrailSqlSessionManager.sqlSessionFactory.getConfiguration().addMapper(DataTrailMapper.class);
            DataTrailSqlSessionManager.sqlSessionFactory.getConfiguration().addInterceptor(new DataTrailInterceptor());
        }else{
            logger.warn("SqlSessionFactory only set once");
        }
    }

}
