package com.helix.demo.java.lang.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author ljy
 * @date 2019/6/13 14:38
 */
public class TypeUtilTest {

    @Test
    public void getLastGenericClass(){
        Arrays.stream(B.class.getDeclaredFields()).forEach(f->{
            Class clazz = TypeUtil.getLastGenericClass(f.getGenericType());
            System.out.println(f.getName());
            System.out.println(clazz.getName());
        });
    }

    /**
     * List<String>
     */
    @Test
    public void getLastClass2() throws Exception{
        Field d = B.class.getDeclaredField("d");
        Class clazz = TypeUtil.getLastGenericClass(d.getGenericType());
        System.out.println(d.getName());
        System.out.println(clazz.getName());
    }


}
