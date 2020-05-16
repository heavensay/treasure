package com.helix.demo.java.lang;

import org.junit.Test;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-04-17 11:08
 */
public class StringTest {

    /**
     * 多个不同字符同时替换；
     */
    @Test
    public void replaceAll(){
        String str = "abcdabcd";
        String reg = "a|c";
        System.out.println(str.replaceAll(reg,","));

        String str2 = "潮流100%棉蓝黑";
        String reg2 = "'|\"|#|&|\\+|%|\\\\|<|>";
        System.out.println(str2);
        System.out.println(str2.replaceAll(reg2,"@"));
    }

}
