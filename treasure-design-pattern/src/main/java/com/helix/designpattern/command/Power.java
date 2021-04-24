package com.helix.designpattern.command;

/**
 * 电源(开关)
 * @author lijianyu
 * @date 2021/4/24 下午6:17
 */
public class Power {

    private boolean isOn = false;

    public void onOrOff(){
        isOn = !isOn;
        if(isOn){
            System.out.println("开灯");
        }else{
            System.out.println("关灯");
        }
    }
}
