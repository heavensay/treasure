package com.helix.designpattern.command;

/**
 * 灯源(强弱)
 * @author lijianyu
 * @date 2021/4/24 下午6:25
 */
public class Light {

    /**
     * 光亮度
     */
    private int lightDeep = 50;

    public void increaseLightDeep(){
        if(lightDeep + 10<=100){
            lightDeep = lightDeep + 10;
            System.out.println("光亮度增加到"+lightDeep);
        }else{
            System.out.println("光亮度已经达到最大"+lightDeep);
        }
    }

    public void decreaseLightDeep(){
        if(lightDeep - 10>=0){
            lightDeep = lightDeep - 10;
            System.out.println("光亮度减少到"+lightDeep);
        }else{
            System.out.println("光亮度已经达到最小"+lightDeep);
        }
    }
}
