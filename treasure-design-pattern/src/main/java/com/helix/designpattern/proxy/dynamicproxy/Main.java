package com.helix.designpattern.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理和静态代理例子
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 15:36
 */
public class Main {
    public static void main(String[] args) {
        Sell sell = new BusinessSell();

        /***动态代理**/

        //加上这句将会产生一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        //DynamicInvocationHandler构造器传的是Object，可看出DynamicInvocationHandler可以处理其他类型的目标类
        Sell dynamicProxy = (Sell)Proxy.newProxyInstance(sell.getClass().getClassLoader(), sell.getClass().getInterfaces(), new DynamicInvocationHandler(sell));
        dynamicProxy.sell();
        /***动态代理**/


        /***静态代理**/
        Sell staticProxy = new StaticProxy(sell);
        staticProxy.sell();
        /***静态代理**/

    }
}
