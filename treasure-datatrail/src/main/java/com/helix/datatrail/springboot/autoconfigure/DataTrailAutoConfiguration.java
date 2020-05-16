package com.helix.datatrail.springboot.autoconfigure;

import com.helix.datatrail.util.DataTrailSqlSessionManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration//开启配置
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class DataTrailAutoConfiguration{

    private static Logger logger = LoggerFactory.getLogger(DataTrailAutoConfiguration.class);

    @Autowired(required = true)
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 初始化Datatrail需要的环境配置
     * @return
     */
    @PostConstruct
    public void initDataTrailConfig() {
        logger.debug("Datatrail component init in springboot container");
        DataTrailSqlSessionManager.registerDataTrailConfig(sqlSessionFactory);
    }
}
