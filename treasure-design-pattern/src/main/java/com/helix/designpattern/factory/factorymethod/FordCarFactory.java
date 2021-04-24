package com.helix.designpattern.factory.factorymethod;

import com.helix.designpattern.factory.Car;
import com.helix.designpattern.factory.FordCar;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 23:16
 */
public class FordCarFactory implements CarFactory {
    @Override
    public Car createCar(String brand) {
        return new FordCar();
    }
}
