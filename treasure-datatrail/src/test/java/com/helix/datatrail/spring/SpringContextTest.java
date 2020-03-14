package com.helix.datatrail.spring;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.mapper.user.UserMapper;
import com.helix.datatrail.util.ThreadLocalSqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.Random;

/**
 * spring结合mybatis环境下，使用DataTrail组件测试
 */
public class SpringContextTest {

    @Test
    public void getUserById(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/helix/datatrail/spring/spring-mybatis.xml");
        UserMapper mapper = context.getBean(UserMapper.class);
        User user = mapper.getUserById(3L);
        Assert.assertNotNull(user);
    }

    /**
     * 创建用户，生成快照数据；
     * 事务各管各的
     */
    @Test
    public void createUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/helix/datatrail/spring/spring-mybatis.xml");
        UserMapper mapper = context.getBean(UserMapper.class);
        User user = new User();
        user.setName("cherry");
        user.setAge(new Random().nextInt(100));
        user.setCreateDate(new Date());

        int result = mapper.createUser(user);
        System.out.println(result);
    }

    /**
     * 同一事务中测试
     * @throws Exception
     */
    @Test
    public void readTwice() throws Exception{

        ApplicationContext context = new ClassPathXmlApplicationContext("com/helix/datatrail/spring/spring-mybatis.xml");
        UserMapper mapper = context.getBean(UserMapper.class);

        //1.获取事务控制管理器
        DataSourceTransactionManager txManager = context.getBean(DataSourceTransactionManager.class);
//        //2.获取事务定义
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        //3.设置事务隔离级别，开启新事务
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
//        //4.获得事务状态
//        TransactionStatus transactionStatus = txManager.getTransaction(def);

        txManager.getDataSource().getConnection().setAutoCommit(true);

        try{
            System.out.println("第1次读开始");
            mapper.getUserById(1L);
            System.out.println("第1次读结束");

            System.out.println("第2次读开始");
            mapper.getUserById(1L);
            System.out.println("第2次读开始");
        }catch(Exception ex){
            System.out.println("exception==============");
            ex.printStackTrace();
//            System.out.println(ex);
//            txManager.commit(transactionStatus);
//            txManager.rollback(transactionStatus);
        }finally {

        }
    }

    /**
     * 同一事务中测试
     * @throws Exception
     */
    @Test
    public void testPlatformTransactionManager() throws Exception{

        ApplicationContext context = new ClassPathXmlApplicationContext("com/helix/datatrail/spring/spring-mybatis.xml");
        UserMapper mapper = context.getBean(UserMapper.class);

        //1.获取事务控制管理器
        DataSourceTransactionManager txManager = context.getBean(DataSourceTransactionManager.class);
        //2.获取事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //3.设置事务隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //4.获得事务状态
        TransactionStatus transactionStatus = txManager.getTransaction(def);

        try{
            User user = new User();
            user.setName("cherry");
//            user.setName("cherrycherrycherrycherrycherrycherrycherrycherrycherry");//超过name数据库定义长度，为了报错回滚
            user.setAge(new Random().nextInt(100));
            user.setCreateDate(new Date());

            int result = mapper.createUser(user);
            System.out.println(result);
            txManager.commit(transactionStatus);
        }catch(Exception ex){
            System.out.println("exception==============");
            ex.printStackTrace();
//            System.out.println(ex);
//            txManager.commit(transactionStatus);
            txManager.rollback(transactionStatus);
        }finally {

        }
    }
}
