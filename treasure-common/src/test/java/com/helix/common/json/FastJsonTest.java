package com.helix.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;

import java.util.ArrayList;
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
    {
        {
            student  = new Student();
            student.setName("tom");

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
        }
    }

    /**
     * 一层查询
     */
    @Test
    public void xpathJson(){
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
}

class Student{
    private String name;

    private List<Home> homes;


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
