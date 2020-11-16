package com.helix.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 15:37
 */
public class DynamicInvocationHandler implements InvocationHandler {
    private Object sell;

    public DynamicInvocationHandler(Object target){
        this.sell = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("dynamic discount 70%");
        Object result = method.invoke(sell, args);
        System.out.println("dynamic add point 100");
        return result;
    }
}
