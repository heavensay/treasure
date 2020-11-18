package com.helix.demo.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambaTest {

    /**
     * 快速过滤重复
     */
    @Test
    public void filterDuplication(){
        List<String> strs = new ArrayList<>();
        strs.add("a");
        strs.add("a");
        strs.add("b");
        strs.add("c");
        strs.add("d");
        strs.add("b");
        strs.add("e");
        strs.add("e");
        strs.add("f");

        //sorted()为了排序，把重复的记录连在一起；否则会bcdbef；多出一个b；
        List<String> distinctStrs = strs.stream().sorted().filter(new Predicate<String>() {
            //去除重复value
            String previous;
            @Override
            public boolean test(String p) {
                if(previous!=null && previous.equalsIgnoreCase(p)) {
                    return false;
                }
                previous=p;
                return true;
            }
        }).collect(Collectors.toList());

        distinctStrs.forEach(System.out::println);
    }

    @Test
    public void reduce(){
        Stream<String> stream = Stream.of("a", "b", "c", "d", "e", "f");
        String reduce = stream.reduce("", (s, s2) -> {
            System.out.println(s + "===" + s2);
            return s + s2;
        }, (s, s2) -> {
            System.out.println(s + "=>>>=" + s2);
            return null;
        });
        System.out.println(reduce);
    }

    /**
     * 定义匿名函数
     */
    @Test
    public void anomyousTest(){
        /**
         *IHello hello = new Ihello(){
         *       public void sayHello(String str){
         *           System.out.println(str);
         *       }
         *}
         *hello.sayHello("world");
         */
        IHello hello = s -> System.out.println(s);
        hello.sayHello("world");
    }

    /**
     * ObjectRef::methodName
     * 所引用的方法其实是Lambda表达式的方法体的实现
     *
     * 引用静态方法调用
     */
    @Test
    public void refStaticMethodTest(){
        IHello hello = System.out::println;
        hello.sayHello(" ref static method ");
    }


}
