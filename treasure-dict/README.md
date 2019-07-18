# treasure-dict

treasure-dict作为字段转换插件，解决通常api接口返回数据实体中，字典字段需要手工转换问题，转为系统框架自动转换。

如User实体中有gender(性别)，一般数据库中存储为0(男)/1(女)；我们返回给前端的时候是要翻译为男或女，代码如下：
~~~java
if("0".equals(user.getGender()){
    user.setGenderText("男");
}else{
     user.setGenderText("女");
}
return user;
~~~
如果返回的是一个List<User>列表，还需要自己遍历。这样代码逻辑繁琐、冗余，此插件就是来解决此问题的。
使字典类字段自动翻译为字典文本值。

## 使用说明
treasure-dict结构图
![structure](https://raw.githubusercontent.com/wiki/heavensay/treasure/treasure-dict-structure.jpg)


### 1依赖引入
~~~xml
    <dependency>
        <groupId>treasure-dict</groupId>
        <artifactId>com.github.heavensay</artifactId>
        <version>1.0.2</version>
    </dependency>
~~~
### 2在需要使用的bean上面，使用@Dict注解进行配置
@Dcit属性介绍：
 *  type:字典类型，e.g. User.class(建议表对应的实体class)，默认值：DefaultType.class
 *  category:字典分类，e.g. User(建议表名)，默认值：DictConfiguration.DEFAULT_CATEGORY
 *  code:字典代码,e.g. gender(建议表中字典字段),DictConfiguration.DEFAULT_CODE
 *  valueColumn:字典值对应的字段名称,框架会从此字段取值，再根据查到的值，获取翻译内容

~~~java
public class HelloBeanRsp {

    private String name;

    private String country;

    @Dict(type = CountryEnum.class,valueColumn = "country")
    private String countryText;
    
    private String gentleman;
    
    @Dict(type=User.class,category="com.User",code = "gender",valueColumn = "gentleman")
    private String gentlemanText;       

    //......
    //some bean get method....
    //some bean set method....
}
~~~

### 3字典数据来源

* 从Enum类中提取字典数据
从枚举类中抽取字典数据，在枚举类中添加@DictEnumSource注释，其中属性：
  * valueFieldName：value字段名称，从枚举中抽取作为字典模式value值；默认值:枚举类名称
  * valueLabelFieldName：valueLabel字段名称，从枚举中抽取作为字典模式valueLabel值

~~~java
@EnumDictSourceMetadata(valueLabelFieldName = "text",valueFieldName = "code")
public enum DigitEnum {

    A(1,"一"),
    B(2,"二"),
    ;

    private Integer code;
    private String text;

    DigitEnum(Integer code, String text){
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
~~~

springboot会自动扫描main启动类所在路径下面所有含@DictEnumSource的枚举类，包括子路径；如果定义在别的路径下，可通过下面属性来进行配置，com.xxx为要进行扫描的路径
`helix.enhance.dict.enum.autocollect.path = com.xxx`


也可以编程式把枚举类加入字典数据源中：
`EnumDictSource.INSTANCE.loadEnumData(DigitEnum.class);`

* 从数据库字典表中提取字典数据

可供参考的字典表(mysql):
~~~sql
create table sys_dict
(
   id                   bigint(10) not null auto_increment,
   pid                  bigint(10) comment '父ID',
   category             varchar(30) not null default 'default_category' comment '分类(通常表名/系统名)',
   code                 varchar(30) not null comment '业务代码分类(通常字典字段名)',
   value                varchar(30) not null comment '业务代码值',
   value_label          varchar(60) comment '业务代码文本说明',
   sortby               int(8) comment '排序优先级编号',
   update_time          datetime comment '创建时间',
   create_time          datetime comment '更新时间',
   primary key (id)
);

alter table sys_dict comment '系统字典表';

/*==============================================================*/
/* Index: idx_category_code_value                               */
/*==============================================================*/
create unique index idx_category_code_value on sys_dict
(
   category,
   code,
   value
);


--字段建议：
--category:User(建议表名),code:gender(建议表中字典字段),value:man(字典值),value_label:男(字典展示文本)

--可供参考的数据：
--INSERT INTO `sys_dict`(`id`, `pid`, `category`, `code`, `value`, `value_label`, `sortby`, `update_time`, `create_time`) VALUES (1, NULL, 'default_category', 'gender', 'lady', '女士', 1, '2019-07-04 10:46:41', '2019-07-04 10:46:43');
--INSERT INTO `sys_dict`(`id`, `pid`, `category`, `code`, `value`, `value_label`, `sortby`, `update_time`, `create_time`) VALUES (2, NULL, 'default_category', 'gender', 'gentleman', '男士', 2, '2019-07-04 10:47:06', '2019-07-04 10:47:08');

~~~

可实现AbstarctDictSource类，来实现从mysql读取sys_dict字典表，注入到系统的字典数据源中：
~~~java
public class SystemDictDataSource extends AbstarctDictSource {

    private SysDictMapper sysDictMapper = SpringContextHolder.getBean(SysDictMapper.class);

    @Override
    public void loadData() {
        Map<DictKey, Object> dictDatas = getDictData();
        List<SysDict> sysDicts = sysDictMapper.listAll();
        sysDicts.stream().forEach(dict->{
            String category = dict.getCategory();
            String code = dict.getCode();
            String value = dict.getValue();
            String valueLabel = dict.getValueLabel();

            DictKey key = new DictKey(category,code,value);
            dictDatas.put(key,valueLabel);
        });
    }
}
~~~

加入字典数据源中：
`SysDictManager.registerDictSource(new SystemDictDataSource());`

### 4springboot工程开启Dict转换
在配置文件中(如application.properties/application.yml)，开始字典翻译功能
`helix.enhance.dict.mapping.enabled = true`

### 5springmvc项目中开启Dict转换
```xml
    <!-- 支持Controller返回值字典自动转换 -->
    <beans:bean class="com.helix.dict.spring.dict.bodyadvice.DictMapperResponseBodyAdvice"/>

    <!-- 下面是针对Enum类自动收集所用 -->
    <beans:bean class="com.helix.dict.EnumDictSourceAutoCollect" init-method="enumDictCollect">
        <!-- packagePath配置为项目路径即可 -->
        <beans:property name="packagePath" value="com.xxx"/>
    </beans:bean>
```

### 其他事项
SysDictManager工具类中，有便捷方法进行操作，
字典自动转换SysDictManager.mapping(Object instance);

### 待完善功能
字典数据源更新还有待优化

## 使用愉快
