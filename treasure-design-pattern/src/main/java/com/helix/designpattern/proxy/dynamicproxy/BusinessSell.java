package com.helix.designpattern.proxy.dynamicproxy;

/**
 * 商家卖东西
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 15:34
 */
public class BusinessSell implements Sell {
    @Override
    public void sell() {
        System.out.println("business sell goods");
    }
}
