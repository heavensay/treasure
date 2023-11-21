package com.helix.demo.mybatis.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

public class MybatisPlusGeneratorUtil2 {

    // TODO 注意：未来每次只需要修改TODO部分
    public static void main(String[] args) {
        // TODO 【每次必须要改】
//        String[] addInclude = {"talkback_group","im_user","im_group","im_group_member","im_send_message",
//                "im_receive_message","im_conversation",};

//        String[] addInclude = {"im_receive_message"};
//        String[] addInclude = {"im_group_member"};
//        String[] addInclude = {"im_receive_system_notice","im_send_system_notice"};
//        String[] addInclude = {"im_watcher"};
//        String[] addInclude = {"im_group"};
//        String[] addInclude = {"talkback_signal_register"};
        String[] addInclude = {"talkback_user_login_log"};
        // 0、数据库配置【数据库连接、用户名、密码】
//        String dbUrl = "jdbc:mysql://localhost:3306/im?useSSL=false&useUnicode=true&characterEncoding=utf-8";
//        String userName = "root";
//        String password = "123456";
        String dbUrl = "jdbc:mysql://172.25.11.51:3306/talkback?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false";
        String userName = "root";
        String password = "abcABC123!";
        String projectDir = "/Users/liyu/Desktop/work/全能数字/mybatisplus-generate";
        String projectName = "talkback";

        // 1、globalConfig【作者、输出目录】
        String author = "lijianyu";
//        String outputDir = "/Users/liyu/Desktop/work/全能数字/mybatisplus-generate/talkback" + "/src/main/java";
        String outputDir = projectDir+"/"+projectName + "/src/main/java";

        // 2、packageConfig【父包名、父包模块名、实体类PO生成路径、Mapper接口生成路径、Mapper.xml生成路径】
//        String parent = "cn.bifrosttech.talkback.center";
        String parent = "cn.bifrosttech.talkback.core";
        String moduleName = "user";
//        String moduleName = "watcher";
//        String moduleName = "group";
        String entityPODir = "model.entity";
        String serviceDir = "service";
        String serviceImplDir = "service.impl";
        String mapperDir = "mapper";
        String xmlDir = "mapper.xml";
//        String mapperXmlDir = System.getProperty("user.dir") + "/src/main/resources/mapper/";
//        String mapperXmlDir = "/Users/liyu/Desktop/work/全能数字/mybatisplus-generate/imserver" + "/src/main/resources/mapper/";

        // 3、strategyConfig【过滤表前缀、自动填充的字段、表名】
        String[] addTablePrefix = {};
        Column[] addTableFills = {new Column("create_time", FieldFill.INSERT),
                new Column("update_time", FieldFill.INSERT_UPDATE),
                new Column("update_by", FieldFill.INSERT_UPDATE),
                new Column("create_by", FieldFill.INSERT),
                new Column("del", FieldFill.INSERT)};

        FastAutoGenerator
            // 数据库配置
            .create(dbUrl, userName, password)
            // 全局配置
            .globalConfig(builder -> {
                builder.author(author)              // 设置作者
                     .commentDate("yyyy-MM-dd HH:mm") //时间
                    .fileOverride()                 // 覆盖已生成文件
                    .outputDir(outputDir)           // 指定输出目录
                    .disableOpenDir();              // 不打开输出目录
            })
            // 包配置
            .packageConfig(builder -> {
                builder.parent(parent)                                                         // 设置父包名
                    .moduleName(moduleName)                                                    // 设置父包模块名
                    .entity(entityPODir)                                                       // 实体类PO生成路径
                    .mapper(mapperDir)                                                         // Mapper接口生成路径
                    .service(serviceDir)                                                           // 设置自定义dao路径
                    .serviceImpl(serviceImplDir)                                                   // 设置自定义daoImpl路径
                    .xml(xmlDir);   // 设置mapperXml生成路径
//                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlDir));   // 设置mapperXml生成路径
            })
            // 策略配置
            .strategyConfig(builder -> {
                builder.addInclude(addInclude)                                    // 设置需要生成的表名，默认为库中所有表
                    .addTablePrefix(addTablePrefix)                               // 设置过滤表前缀
                    .mapperBuilder().enableBaseResultMap()                        // 开启表字段映射、cache()默认不开启
                    .entityBuilder().addTableFills(addTableFills).enableLombok()  // 设置自动填充的时间字段、开启lombok模型
                ;
            })
            // 模板配置
            .templateConfig(builder ->
                builder
                    .controller("/templates/controller.java")                             // 设置不生成controller
                    .service("/templates/service.java")                                // 设置不生成service
                    .serviceImpl("/templates/serviceImpl.java")                            // 设置不生成serviceImpl
                    .entity("/templates/entity.java")       // 设置entity模板
                    .mapper("/templates/mapper.java")       // 设置mapper接口模版
                    .mapperXml("/templates/mapper.xml")     // 设置mapper.xml模板
            )
            // 模板引擎
            .templateEngine(new FreemarkerTemplateEngine())                  // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            // 注入配置
            // .injectionConfig()
            // 执行
            .execute();
    }
}