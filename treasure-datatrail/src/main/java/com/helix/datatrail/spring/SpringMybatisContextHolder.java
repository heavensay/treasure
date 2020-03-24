package com.helix.datatrail.spring;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 持有spring环境；
 */
public class SpringMybatisContextHolder implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public static boolean isInjectApplicationContext(){
        return context != null;
    }

    public static SqlSessionFactory obtainSqlSessionFactory(){
        return context.getBean(SqlSessionFactory.class);
    }

    public static Object getBean(Class clazz){
        return context.getBean(clazz);
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
