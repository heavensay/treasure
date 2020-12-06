package com.helix.designpattern.decorator;

/**
 * 装饰者decorator模式demo
 *
 * 以{@code Room}为基础，有毛坯房、别墅，价格不一样；房租需要装修，加上装修越多，费用越多
 * @author lijianyu@yunloan.net
 * @date 2020-12-06 16:14
 */
public class Main {
    public static void main(String[] args) {
        House roughHouse = new MarbleDecotor(new FloorDecotor(new RoughHouse()));
        System.out.println(roughHouse.desc()+" cost:"+roughHouse.cost());

        House VillaHouse = new FloorDecotor(new VillaHouse());
        System.out.println(VillaHouse.desc()+" cost:"+VillaHouse.cost());

    }
}
