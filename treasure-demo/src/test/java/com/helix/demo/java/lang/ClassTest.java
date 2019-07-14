package com.helix.demo.java.lang;

import org.junit.Test;

public class ClassTest {

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

    /**
     * 基础类型class和保证类型class比较
     * 结果：不相等
     */
    @Test
    public void primitiveClassTest(){
        System.out.println(int.class == Integer.class);
    }

}
