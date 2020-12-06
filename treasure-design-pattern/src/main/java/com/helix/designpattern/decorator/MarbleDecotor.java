package com.helix.designpattern.decorator;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:10
 */
public class MarbleDecotor extends House{
    private House target;

    public MarbleDecotor(House house){
        target = house;
    }

    @Override
    public double cost() {
        return target.cost()+2;
    }

    @Override
    public String desc() {
        return target.desc()+",marble";
    }
}
