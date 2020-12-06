package com.helix.designpattern.decorator;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:08
 */
public class VillaHouse extends House {
    @Override
    public double cost() {
        return 80000;
    }

    @Override
    public String desc() {
        return "villa house";
    }
}
