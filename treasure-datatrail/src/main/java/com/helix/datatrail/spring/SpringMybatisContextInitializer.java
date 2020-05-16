package com.helix.datatrail.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author lijainyu
 * @date 2020-03-24 18:02
 */
public class SpringMybatisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Logger logger = LoggerFactory.getLogger(SpringMybatisContextInitializer.class);


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.debug("SpringMybatisContextInitializer initialize");
        SpringMybatisContextHolder.assignApplicationContext(applicationContext);
    }

//    /**
//     * 初始化Datatrail需要的环境配置
//     * @return
//     */
//    @PostConstruct
//    public void initDataTrailConfig() {
//        logger.debug("Datatrail component init in springboot container");
//        DataTrailSqlSessionManager.initDataTrailConfig(SpringMybatisContextHolder.obtainSqlSessionFactory());
//    }

}
