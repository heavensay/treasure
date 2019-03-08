package com.helix.common;

/**
 * @author lijianyu
 * @date 2019/3/1 18:55
 */
public class Asserts {
    public static void notNull(Object instance){
        if(instance == null){
            throw new NullPointerException("参数不能为空");
        }
    }
}
