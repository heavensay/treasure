package com.helix.spring.validation;

import com.helix.spring.validation.group.ValidationUserAdd;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.ValidationException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestValidation {


    @Test
    public void testSimpleValid(){
        User user = new User();
//        user.setName("tom");
//        user.setAge(5);
        ValidationUtil.validate(user);
    }

    /**
     * 正则匹配测试
     */
    @Test
    public void testRegRexValid(){
        User user = new User();
        user.setName("tom");
        user.setAge(16);
        user.setId(1);
        ValidationUtil.validate(user);
    }

    /**
     * 年龄规则测试
     * 国际化信息输出
     */
    @Test
    public void testAgeValid(){
        User user = new User();
        user.setName("tom");
        user.setAge(10);
        user.setId(1);
//        ValidationUtil.validate(user);
        ValidationUtil.validate(user,ValidationUserAdd.class);
    }

    /**
     * 年龄group分组规则测试
     */
    @Test
    public void testAgeGroupValid(){
        User user = new User();
        user.setName("tom");
        user.setAge(12);
        user.setId(1);
        ValidationUtil.validate(user,ValidationUserAdd.class);

        user.setAge(8);
        ValidationUtil.validate(user,ValidationUserAdd.class);
    }

    @Test
    public void nestValid(){
        User user = new User();
        user.setName("tom");
        user.setAge(11);
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);
        ValidationUtil.validate(user);
    }


    @Rule
    public ExpectedException exceptedEx = ExpectedException.none();

    /**
     * 测试自定义的validation注解标记和逻辑验证类
     */
    @Test
    public void testCustomAnnotationValid(){
        User user = new User();
        List list = new ArrayList();
        list.add("father");
        list.add(null);
        list.add("mother");
        user.setRelations(list);

        exceptedEx.expect(ValidationException.class);
        exceptedEx.expectMessage("list中不能");

        ValidationUtil.validate(user);
    }


    @Test
    public void validMethodParams()throws Exception{
        User user = new User();
        user.setName("tom");
        user.setAge(11);
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);

        UserService userService = new UserService();
        Method m1 = userService.getClass().getMethod("addUser",User.class,String.class);
        ValidationUtil.validateProperty(userService,m1,new Object[]{user,null},new Class<?>[0]);
    }

    @Test
    public void validMethodParamsAll()throws Exception{
        User user = new User();
        user.setName("tom中");
        user.setAge(11);
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);

        UserService userService = new UserService();
        Method m1 = userService.getClass().getMethod("addUser",User.class,String.class);
        ValidationUtil.validatePropertyCompliable(userService,m1,new Object[]{user,null},new Class<?>[0]);
    }

    /**
     * 支持方法上@Validated注释分组验证；以及方法参数@Validated注释的验证
     *
     * @throws Exception
     */
    @Test
    public void validateMethodProperty()throws Exception{
        User user = new User();
        user.setName("tom中");
        user.setAge(11);
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);

        UserService userService = new UserService();
        Method m1 = userService.getClass().getMethod("createUser",User.class,String.class);
        ValidationUtil.validatePropertyCompliable(userService,m1,new Object[]{user,null});
    }
}
