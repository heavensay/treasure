采用AOP+注解进行拦截实现数据快照功能

##准备功能：
创建统一的快照表用于存储所有业务表所需的快照数据

##实现功能：
1针对需要快照的数据表，针对insert、update、delete进行拦截，存入快照表
2提供id查询功能和基于操作时间的快照查询；
3提供多张有关联的表快照，和关联查询；

###关联的资源
使用mysql，durid，jsonfast来实现；
数据库针对mysql；

###主意使用
1快照表的表名可以自定义，但是表字段是确定的；
```
   id                   bigint(10) not null auto_increment  comment '',
   ops_time             datetime  comment '操作时间',
   ops_event            varchar(20)  comment '操作时间(insert,update,delete)',
   ops_object_name      varchar(30)  comment '操作业务表',
   ops_object_id        bigint(10)  comment '操作业务表id',
   ops_object_content   varchar(3000)  comment '操作业务数据内容',
```

###遗留问题
批量增删数据的，历史流水不能保存；可以考虑批量操作的时候查询出所有数据，在更新到日志表中；
表名可以配置，但是历史流水表的字段是固定的，不能变更；

###使用说明
项目要支持mybatis

支持springboot，
需要项目已经支持mybatis，
####1没有spring环境，使用datatrail组件
1在业务数据更新前，设置DataTrail组件所需的SqlSession:ThreadLocalSqlSession.put(sqlSession);

2在业务数据更新后，释放线程关联的SqlSession:ThreadLocalSqlSession.clear();

~~~
try {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //1在业务数据更新前，设置DataTrail组件所需的SqlSession
    ThreadLocalSqlSession.put(sqlSession);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = new User();
    int result = mapper.createUser(user);
    sqlSession.commit();
}finally {
    //2在业务数据更新后，释放线程关联的SqlSession
    ThreadLocalSqlSession.clear();
}
~~~
通常来说，更新操作的流程中，事务是手动提交的；以保证业务更新和快照数据的插入同时生效或回滚

####Spring环境下，使用datatrail组件
需要把spring context注入到datatrail组件中，引入如下配置即可；
~~~
    <bean class="com.helix.datatrail.spring.SpringMybatisContextHolder"></bean>    
~~~

####SpringBoot环境下，使用datatrail组件



