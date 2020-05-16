#DataTrail组件
在业务层进行数据快照的存储，采用AOP+注解进行数据拦截实现数据快照功能。

快照数据主要用于查看某个时间点数据信息，而非最新的数据信息；比如：老是审核一个同学请假单，第一次审批拒绝；同学重新填写请假理由，再次提交，审批通过。需要能查看在审批拒绝时候同学的请假理由，而不是重新提交之后的请假理由

##实现功能
* 针对需要快照的数据表，针对insert、update、delete进行拦截，存入快照表
* 提供id查询功能和基于操作时间的快照查询；

##使用说明

###环境准备

1. 快照表创建

创建统一的快照表用于存储所有业务表所需的快照数据
快照表的表名可以自定义，但是表字段是确定的；mysql示例表：
```
CREATE TABLE `ops_history` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `ops_time` datetime DEFAULT NULL COMMENT '操作时间',
  `ops_event` varchar(20) DEFAULT NULL COMMENT '操作时间(insert,update,delete)',
  `ops_object_name` varchar(30) DEFAULT NULL COMMENT '操作业务表',
  `ops_object_id` bigint(10) DEFAULT NULL COMMENT '操作业务表id',
  `ops_object_content` varchar(3000) DEFAULT NULL COMMENT '操作业务数据内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='数据操作记录表';
```

2. Maven Dependency

```xml
    <dependency>
        <groupId>com.github.heavensay</groupId>
        <artifactId>treasure-datatrail</artifactId>
        <version>${revision}</version>
        <type>pom</type>
    </dependency>
```

3. 项目需要支持mybatis，spring/springboot


###没有spring环境，使用datatrail组件
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

###Spring环境下，使用datatrail组件
需要把spring context注入到datatrail组件中，引入如下配置即可；
~~~
    <bean class="com.helix.datatrail.spring.SpringMybatisContextHolder"></bean>    
~~~

###SpringBoot环境下，使用datatrail组件
引入datatrail就自动支持；

###待优化
批量增删数据的，历史流水不能保存；可以考虑批量操作的时候查询出所有数据，在更新到日志表中；
表名可以配置，但是历史流水表的字段是固定的，不能变更；
