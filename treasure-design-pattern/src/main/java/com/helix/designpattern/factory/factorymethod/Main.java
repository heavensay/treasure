package com.helix.designpattern.factory.factorymethod;

import com.helix.designpattern.factory.Car;
import com.helix.designpattern.factory.simple.SimpleCarPlant;

/**
 * 简单工厂
 * 并不是一个真正的模式，但是经常被使用，更像是一种编程习惯。这种模式把创建实例的逻辑放到一个专门的类中，由他统一来处理。
 * 扩展性差，如果要加一种类别，这个工厂类还是需要改动。
 *
 * @author lijianyu@yunloan.net
 * @date 2020-12-22 22:57
 */
public class Main {

    public static void main(String[] args) {

        String brand = "ford";

    }

}
