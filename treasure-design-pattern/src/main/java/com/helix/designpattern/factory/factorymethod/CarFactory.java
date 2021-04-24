package com.helix.designpattern.factory.factorymethod;

import com.helix.designpattern.factory.Car;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 23:14
 */
public interface CarFactory {
    Car createCar(String brand);
}
