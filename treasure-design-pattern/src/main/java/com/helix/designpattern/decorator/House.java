package com.helix.designpattern.decorator;

/**
 * 房屋
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:03
 */
public abstract class House {

    /**
     * 花费金额
     * @return
     */
    public abstract double cost();

    /**
     * 明细
     * @return
     */
    public abstract String desc();

}
