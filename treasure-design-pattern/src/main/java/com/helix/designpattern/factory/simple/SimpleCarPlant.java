package com.helix.designpattern.factory.simple;

import com.helix.designpattern.factory.AudiCar;
import com.helix.designpattern.factory.Car;
import com.helix.designpattern.factory.FordCar;

/**
 * 简单工厂，针对car的实例化都集中在此；
 *
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 23:06
 */
public class SimpleCarPlant {

    public Car createCar(String brand) {
        Car car;
        if ("ford".equals(brand)) {
            car = new FordCar();
        } else {
            car = new AudiCar();
        }

        return car;
    }

}
