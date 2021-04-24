package com.helix.designpattern.factory.factorymethod;

import com.helix.designpattern.factory.AudiCar;
import com.helix.designpattern.factory.Car;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 23:16
 */
public class AudiCarFactory implements CarFactory {
    @Override
    public Car createCar(String brand) {
        return new AudiCar();
    }
}
