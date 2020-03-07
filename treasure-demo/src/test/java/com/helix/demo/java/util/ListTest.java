package com.helix.demo.java.util;

import org.junit.Test;

import java.util.ArrayList;

public class ListTest {

    /**
     * list排序
     */
    @Test
    public void listSortTest(){
        ArrayList<Integer> list = new ArrayList();
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(4);
        list.add(2);
        list.add(6);

        list.sort((o1, o2) -> {
            if(o1<o2){
                return -1;
            }else{
                return 1;
            }
        });
        list.forEach(e->{
            System.out.println(e);
        });
    }
}
