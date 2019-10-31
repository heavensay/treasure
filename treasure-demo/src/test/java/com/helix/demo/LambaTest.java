package com.helix.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

}
