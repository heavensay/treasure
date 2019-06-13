package com.helix.common.json;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * alibaba fastjson测试
 * 可当做对象查询语言（OQL）来使用
 * 参考https://blog.csdn.net/liupeifeng3514/article/details/79180154  fastjson（九）JSONPath 的使用
 * jsonarray{[
 *      jsonobect(object),jsonobject(obejct)
 * ]}
 * @author lijianyu
 * @date 2019/2/22 18:49
 */
public class FastJsonTest {
    private Student student;

    private JSONObject studentJsonObject;

    private String studentJsonStr;

    {
        {
            student  = new Student();
            student.setName("tom");
            student.setOpenTime(new Date());

            Address address1 = new Address();
            address1.setStreet("s1");
            address1.setDoorNo("d1");
            Address address2 = new Address();
            address2.setStreet("s2");
            address2.setDoorNo("d2");
            Address address3 = new Address();
            address3.setStreet("s2");
            address3.setDoorNo("d3");

            List list = new ArrayList();
            list.add(address1);
            list.add(address2);
            list.add(address3);

            Home home1 = new Home();
            home1.setHomeName("home1");
            home1.setAddresses(list);

            Home home2 = new Home();
            home2.setHomeName("home2");
            home2.setAddresses(list);
            List homeList = new ArrayList();
            homeList.add(home1);
            homeList.add(home2);

            student.setHomes(homeList);

            //在json text中导航查找数据
            studentJsonObject = JSON.parseObject(JSON.toJSONString(student));
            studentJsonStr = JSON.toJSONString(student);
        }
    }

    /**
     * 一层查询
     */
    @Test
    public void pathJson(){
        System.out.println(JSON.toJSONString(student));
        System.out.println("===================");
        //在json text中导航查找数据

        Object e4 = JSONPath.eval(studentJsonObject,"$.homes");
        System.out.println("$.homes  type=JSONArray  content:"+e4);

        Object e42 = JSONPath.eval(studentJsonObject,"$.homes[0]");
        System.out.println("$.homes[0]  type=JSONObject  content:"+e42);

        Object e43 = JSONPath.eval(studentJsonObject,"$.homes[homeName='home1']");
        System.out.println("$.homes[homeName='home1']  type=JSONArray  content:"+e43);

        Object e432 = JSONPath.eval(studentJsonObject,"$.homes[homeName='home1'][0]");
        System.out.println("$.homes[homeName='home1'][0]  type=JSONArray  content:"+e432);

        Object e44 = JSONPath.eval(studentJsonObject,"$.homes[50]");
        System.out.println("$.homes[50] content: "+e44);
    }

    /**
     * 深层次查询
     */
    @Test
    public void deepSearch(){
        Object e1 = JSONPath.eval(studentJsonObject,"$.homes[homeName='home1'].addresses[street='s2']");
        System.out.println("$.homes[homeName='home1'].addresses[street='s2']  content:"+e1);

        Object e2 = JSONPath.eval(studentJsonObject,"$.homes[homeName='home1'][0].addresses[street='s2']");
        System.out.println("$.homes[homeName='home1'][0].addresses[street='s2'] content:"+e2);

        Object e3 = JSONPath.eval(studentJsonObject,"$.homes.addresses[street='s2']");
        System.out.println("$.homes.addresses[street='s2'] content:"+e3);
    }

    /**
     * json节点属性
     */
    @Test
    public void join(){
        JSONObject jsonOne = new JSONObject();
        JSONObject jsonTwo = new JSONObject();
        JSONArray jsonThree = new JSONArray();
        JSONObject jsonThree1_1 = new JSONObject();
        jsonThree1_1.put("a","1-1-1");
        jsonThree1_1.put("b","1-1-2");
        JSONObject jsonThree1_2 = new JSONObject();
        jsonThree1_2.put("a","1-2-1");
        jsonThree1_2.put("b","1-2-2");

        jsonOne.put("name", "kewen");
        jsonOne.put("age", "24");

        jsonTwo.put("hobbit", "Dota");
        jsonTwo.put("hobbit2", "wow");

        jsonThree.add(jsonThree1_1);
        jsonThree.add(jsonThree1_2);

        JSONObject jsonFour = new JSONObject();

        jsonFour.putAll(jsonOne);
        jsonFour.putAll(jsonTwo);
        jsonFour.put("arrayObjectTest",jsonThree);

        System.out.println(jsonFour.toString());
        //{"hobbit":"Dota","hobbit2":"wow","name":"kewen","arrayObjectTest":[{"a":"1-1-1","b":"1-1-2"},{"a":"1-2-1","b":"1-2-2"}],"age":"24"}
    }


    /**
     * 按照json xpath规则设置属性值
     */
    @Test
    public void set(){
        JSONObject jsonObject = new JSONObject();
        JSONPath.set(jsonObject,"$user.name","tom");
        System.out.println(jsonObject.toJSONString());
        JSONPath.set(jsonObject,"$user.name","");
        System.out.println(jsonObject.toJSONString());
    }


    /**
     * 从json字符串中查找path查询内容
     */
    @Test
    public void extract(){
        Object r = JSONPath.extract(studentJsonStr,"$.homes");
        System.out.println("type:"+r.getClass()+",content:"+r.toString());

        Object name = JSONPath.extract(studentJsonStr,"$.name");
        System.out.println("type:"+name.getClass()+",content:"+name.toString());

        Object openTime = JSONPath.extract(studentJsonStr,"$.openTime");
        System.out.println("type:"+openTime.getClass()+",content:"+openTime.toString());

        System.out.println(JSON.parseObject("{'fff':123}",String.class));
    }

    /**
     * eval直接从json字符串中进行path搜索，找不到；可以使用{@link JSONPath#extract(String, String)}方法来进行搜索
     */
    @Test
    public void eval(){
        Object result = JSONPath.eval(studentJsonStr,"$");
        Assert.assertNotNull(result);

        Object name = JSONPath.eval(studentJsonStr,"$.name");
        Assert.assertNull(name);
    }

    /**
     * 反序列化能够自动识别如下日期格式
     * ISO-8601日期格式
     * yyyy-MM-dd
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * 毫秒数字
     * 毫秒数字字符串
     * .NET JSON日期格式
     * new Date(198293238)
     */
    @Test
    public void deserializationDate() {
//        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd";//设置日期格式,全局设置
        Date date = new Date();
        //输出毫秒值
        System.out.println(JSON.toJSONString(date));
        //默认格式为yyyy-MM-dd HH:mm:ss
        System.out.println(JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat));
        //根据自定义格式输出日期   第一个参数是obj类型
        System.out.println(JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));

        String json = "{'openTime':'1560324503429'}";
        Student student = JSON.parseObject(json,Student.class);
        String json2 = "{'openTime':'2019-06-06'}";
        Student student2 = JSON.parseObject(json2,Student.class);
        Assert.assertNotNull(student2.getOpenTime());
    }

    /**
     * 数组/列表转换
     */
    @Test
    public void array(){
        String json = "['a','b']";
        JSONArray jsonArray = (JSONArray)JSON.parse(json);
        Assert.assertTrue(jsonArray.toArray(new String[0]).length>0);

        List<String> list = JSON.parseArray(json,String.class);
        Assert.assertTrue(list.size()>0);
    }

    /**
     * 具有泛型的POJO反序列化
     */
    @Test
    public void genericDeserializale(){
        BeanParent<BeanSon> beanParent = new BeanParent<BeanSon>();
        beanParent.setName("parent");
        BeanSon beanSon = new BeanSon();
        beanSon.setLabel("son");
        beanParent.setData(beanSon);
        BeanGrandson beanGrandson = new BeanGrandson();
        beanGrandson.setAge(18);
        beanSon.setResult(beanGrandson);

        String json = JSON.toJSONString(beanParent);

        //通过构造新的Class(Class也是Type的父类)实例，来表名反序列化对象类型
        BeanParent bp = JSON.parseObject(json,new TypeReference<BeanParent<BeanSon<BeanGrandson>>>(){});
        Assert.assertNotNull(bp.getData());


        //通过构造Type实例，来表名反序列化对象类型
//        ParameterizedType parentType = ParameterizedTypeImpl.make(BeanParent.class,new Type[]{BeanSon.class},null);
        ParameterizedType sonType = ParameterizedTypeImpl.make(BeanSon.class,new Type[]{BeanGrandson.class},null);
        ParameterizedType parentType = ParameterizedTypeImpl.make(BeanParent.class,new Type[]{sonType},null);
        BeanParent bp2 = JSON.parseObject(json,parentType);
        Assert.assertNotNull(bp2.getData());


    }
}

class Student{
    private String name;

    private List<Home> homes;

    private Date openTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void setHomes(List<Home> homes) {
        this.homes = homes;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}

class Home{
    private String homeName;
    private List<Address> addresses;

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}

class Address{
    private String street;

    private String doorNo;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }
}
