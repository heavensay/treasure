package com.helix.designpattern.factory.simple;

import com.helix.designpattern.factory.Car;

/**
 * 简单工厂
 *
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 22:57
 */
public class CarShop {

    private SimpleCarPlant factory;

    public CarShop(SimpleCarPlant factory) {
        this.factory = factory;
    }

    public Car buyCar(String brand) {
        Car car;

        //简单工厂把new car()逻辑移到CarFactory工厂中；
//        if("ford".equals(brand)){
//            car = new FordCar();
//        }else{
//            car = new AudiCar();
//        }
        car = factory.createCar(brand);


        System.out.println("上牌===");

        System.out.println("装导航===");

        System.out.println("车管所办理提车业务===");

        System.out.println("可以提车===");

        return car;
    }

}
