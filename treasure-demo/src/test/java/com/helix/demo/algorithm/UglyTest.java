package com.helix.demo.algorithm;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijianyu
 * @date 2023/5/25 16:57
 */
public class UglyTest {

    /**
     * 丑数计算，根据传入n，获取第n个丑数
     * 丑数是质因数只有2，3，5的整数，还有一个特殊丑数是1；
     * 1暴力法：整数%2%3%5来获得，效率比较低下；
     * 2根据规则*2*3*5，来一个个获的丑数，比较高效；
     * 此测试用例使用方法2
     * 3个指针p，指向已经相乘过的最小丑数，每次比较能获得一个最小丑数。继而把最小指针往下一个丑数移动
     */
    @Test
    public void uglyCalc(){
        int n = 11;

        List<Integer> list = new ArrayList<>();
        list.add(1);

        int p2 = 1;
        int p3 = 1;
        int p5 = 1;

        for (int i=0;i<n-1;i++){
            int num2 = list.get(p2-1) * 2;
            int num3 = list.get(p3-1) * 3;
            int num5 = list.get(p5-1) * 5;

            int numMin = Math.min(Math.min(num2,num3),num5);
            if (num2 == numMin) p2++;
            if (num3 == numMin) p3++;
            if (num5 == numMin) p5++;
            list.add(numMin);
        }
        System.out.println(list.get(list.size()-1));

        for (Integer ugly : list) {
            System.out.print(ugly+" ");
        }
    }
}
