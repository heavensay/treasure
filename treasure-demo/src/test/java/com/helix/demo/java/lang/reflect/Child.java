package com.helix.demo.java.lang.reflect;

/**
 * @author ljy
 * @date 2019/7/15 11:31
 */
public class Child extends Parent{

    private String name;

    private String age;

    public Integer size;

    private static String staticField;

    public static String publicStaticField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
