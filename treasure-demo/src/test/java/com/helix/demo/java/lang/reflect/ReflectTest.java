package com.helix.demo.java.lang.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ljy
 * @date 2019/7/15 11:48
 */
public class ReflectTest {

    /**
     * Note：
     * field.getDeclaredFields获取当前类的所有field，不包括父类的；
     *
     * @throws Exception
     */
    @Test
    public void classInfo() throws Exception{
        System.out.println("======getDeclaredFields======");
        Class superClazz = Child.class;
        while (superClazz!=null){
            System.out.println("======class："+superClazz.getName()+"，getDeclaredFields======");
            Field[] declaredFieldsFields = superClazz.getDeclaredFields();
            for (Field declaredFieldsField : declaredFieldsFields) {
                System.out.println("name:"+declaredFieldsField.getName());
            }
            superClazz = superClazz.getSuperclass();
        }
        Child.class.getSuperclass();

        System.out.println("======getFields======");
        Field[] fields = Child.class.getFields();
        for (Field field : fields) {
            System.out.println("name:"+field.getName());
        }
    }

    /**
     * getFields:获取public field信息，包括父类的信息;
     */
    @Test
    public void getFields(){
        System.out.println("======getFields");
        for (Field field : Child.class.getFields()) {
            System.out.println("name:"+field.getName());
        }
    }

    /**
     * getDeclaredFields获取class的所有field(包括private，protected，public)，不会获取父类的field
     */
    @Test
    public void getDeclaredFields(){
        System.out.println("======getDeclaredFields");
        for (Field field : Child.class.getDeclaredFields()) {
            System.out.println("name:"+field.getName());
        }
    }

    /**
     * 获取所有public方法，包括继承的父类，实现的接口
     */
    @Test
    public void getMethods(){
        System.out.println("======getMethods");
        for (Method m : Child.class.getMethods()) {
            System.out.println("name:"+m.getName());
        }
    }

    /**
     * 获取class所有方法(包括public ,protected,private)，不包括继承的父类方法
     */
    @Test
    public void getDeclaredMethods(){
        System.out.println("======getDeclaredMethods");
        for (Method m : Child.class.getDeclaredMethods()) {
            System.out.println("name:"+m.getName());
        }
    }

    /**
     * Class.isPrimitive()测试，是否基础类型
     * String，基础类型的包装类=false
     */
    @Test
    public void isPrimitiveTest(){
        Class stringClass=String.class;
        System.out.println("String is primitive type："+stringClass.isPrimitive());//false

        Class booleanClass=Boolean.class;
        System.out.println("Boolean is primitive type："+booleanClass.isPrimitive());//false

        Class booleanType=boolean.class;
        System.out.println("boolean is primitive type："+booleanType.isPrimitive());

        Class byteType=byte.class;
        System.out.println("byte is primitive type："+byteType.isPrimitive());

        Class charType=char.class;
        System.out.println("char is primitive type："+charType.isPrimitive());

        Class shortType=short.class;
        System.out.println("short is primitive type："+shortType.isPrimitive());

        Class intType=int.class;
        System.out.println("int is primitive type："+intType.isPrimitive());

        Class integerType=Integer.class;
        System.out.println("Integer is primitive type："+integerType.isPrimitive());//false


        Class longType=long.class;
        System.out.println("long is primitive type："+longType.isPrimitive());

        Class floatType=float.class;
        System.out.println("float is primitive type："+floatType.isPrimitive());

        Class doubleType=double.class;
        System.out.println("double is primitive type："+doubleType.isPrimitive());

        Class voidType = void.class;
        System.out.println("void is primitive type："+voidType.isPrimitive());

        Class VoidType = Void.class;
        System.out.println("Void is primitive type："+VoidType.isPrimitive());//false
    }

    @Test
    public void arrayClassInfo()throws Exception{
        Child c1 = new Child();
        Child c2 = new Child();
        Child[] cs = new Child[]{c1,c2};
        Class arrayClass = cs.getClass();
        System.out.println("class child info");
        printClassInfo(Child.class);

        System.out.println("class child[] info");
        printClassInfo(cs.getClass());
    }

    /**
     * 基础类型class和保证类型class比较
     * 结果：不相等
     */
    @Test
    public void primitiveClassTest(){
        System.out.println(int.class == Integer.class);//false
    }


    /**
     * 打印class信息
     * @param clazz
     * @throws Exception
     */
    private void printClassInfo(Class clazz) throws Exception{
        Class superClazz = clazz;
        while (superClazz!=null){
            System.out.println("class:"+superClazz.getName()+",field:====");
            Field[] declaredFieldsFields = superClazz.getDeclaredFields();
            for (Field declaredFieldsField : declaredFieldsFields) {
                System.out.println("name:"+declaredFieldsField.getName());
            }
            System.out.println("class:"+superClazz.getName()+",field:====");

            System.out.println();

            System.out.println("class:"+superClazz.getName()+",method:====");
            for (Method method:superClazz.getDeclaredMethods()){
                System.out.println(method.toString());
            }
            System.out.println("class:"+superClazz.getName()+",method:====");

            superClazz = superClazz.getSuperclass();
            System.out.println();
            System.out.println();
            System.out.println();
        }

    }
}
