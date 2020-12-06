package com.helix.designpattern.decorator;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:06
 */
public class RoughHouse extends House {
    @Override
    public double cost() {
        return 10000;
    }

    @Override
    public String desc() {
        return "rough house";
    }
}
