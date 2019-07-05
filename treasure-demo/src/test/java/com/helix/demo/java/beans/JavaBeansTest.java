package com.helix.demo.java.beans;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * BeanInfo:提供有关Bean的显式信息的接口，其中包含类的属性，方法、事件等显式信息，可以通过Introspector底层反射机制获取
 * Introspector:用于构建一个一个全面描述目标bean的BeanInfo对象，使用低层次的反射来研究类的方法，并应用标准设计模式来标识属性存储器、事件源或公共方法。然后深入分析类的超类，并从它那里添加信息
 * PropertyDescriptor:描述 Java Bean 通过一对存储器方法导出的一个属性以及该属性的getter和setter方法
 *
 * @author ljy
 * @date 2019/7/4 15:00
 */
public class JavaBeansTest {

    /**
     * 获取bean的信息
     * @throws Exception
     */
    @Test
    public void beanInfo() throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(Bean.class);

        Arrays.stream(beanInfo.getPropertyDescriptors()).forEach( pd ->{
            print("name",pd.getName());
            print("name",pd.getDisplayName());
        });
    }

    /**
     * 执行bean write和read方法
     * @throws Exception
     */
    @Test
    public void invokePropertiesMethod()throws Exception{
        Bean bean = new Bean();
        BeanInfo beanInfo = Introspector.getBeanInfo(Bean.class);

        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            if("name".equals(propertyDescriptor.getName())){
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Method readMethod = propertyDescriptor.getReadMethod();
                writeMethod.invoke(bean,"tom");
                Object result = readMethod.invoke(bean,null);
                print(result);
            }
//            if("isLady".equals(propertyDescriptor.getName())){//查询不到isLady属性
            if("lady".equals(propertyDescriptor.getName())){
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Method readMethod = propertyDescriptor.getReadMethod();
                writeMethod.invoke(bean,true);
                Object result = readMethod.invoke(bean,null);
                print(result);
            }
        }
    }


    private void print(Object str){
        System.out.println(str);
    }
    private void print(String sign,Object str){
        System.out.println(sign+":"+str);
    }
}
