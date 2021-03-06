package com.helix.demo.java.lang;

import org.junit.Test;

/**
 * @author ljy
 * @date 2019/6/13 11:27
 */
public class ObjectTest {
    /**
     * 字构造器会调用父构造器
     */
    @Test
    public void constructor(){
        Son son = new Son();
        System.out.println("==============");
        Son son2 = new Son("tom");
        System.out.println("==============");
        Son son3 = new Son("tom",18);
    }

    /**
     *
     */
    @Test
    public void fieldExtendTest(){
        Parent p = new Son();
        System.out.println(p.getName());

        Son s = new Son();
        System.out.println(s.getName());
    }
}

class Parent{
    public String name = "Parent";

    public Parent(){
        System.out.println("i am parent");
    }

    public Parent(String name){
        System.out.println("i am parent:"+name);
    }

    public String getName() {
        return name;
    }
}

class Son extends Parent{
//    public String name = "Son";

    public Integer name = 1;

    public Son(){
        System.out.println("i am son");
    }

    public Son(String name){
        System.out.println("i am son:"+name);
    }

    public Son(String name,Integer age){
        super(name);
        System.out.println("i am son:"+name);
    }
}