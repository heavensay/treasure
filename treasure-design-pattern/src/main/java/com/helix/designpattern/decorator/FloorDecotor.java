package com.helix.designpattern.decorator;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:10
 */
public class FloorDecotor extends House{
    private House target;

    public FloorDecotor(House house){
        target = house;
    }

    @Override
    public double cost() {
        return target.cost()+1;
    }

    @Override
    public String desc() {
        return target.desc()+",floor";
    }
}
