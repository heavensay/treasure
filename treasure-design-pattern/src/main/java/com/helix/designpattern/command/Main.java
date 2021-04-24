package com.helix.designpattern.command;

/**
 * 命令模式
 * 通常情况下，行为发送者和接收者之间是一种紧耦合，命令模式就是将一组行为封装成对象，实现两者之间的松耦合
 *
 * @author lijianyu
 * @date 2021/4/24 下午5:34
 */
public class Main {

    public static void main(String[] args) {
        //创建具体的设备
        Light light = new Light();
        Power power = new Power();

        //创建灯源强弱相关命令
        LightIncreaseCommand lightIncreaseCommand = new LightIncreaseCommand(light);
        LightDecreaseCommand lightDecreaseCommand = new LightDecreaseCommand(light);

        //创建灯源命令
        PowerChangeCommand powerChangeCommand = new PowerChangeCommand(power);

        //把命令设置到台灯控制器中
        DeskLampControl deskLamp = new DeskLampControl();
        deskLamp.setIncreaseLightDeepCommand(lightIncreaseCommand);
        deskLamp.setDecreaseLightDeepCommand(lightDecreaseCommand);
        deskLamp.setOnorOffCommand(powerChangeCommand);

        //测试台灯命令是否起作用
        deskLamp.onOrOffLight();
        deskLamp.onOrOffLight();
        deskLamp.onOrOffLight();

        deskLamp.increase();
        deskLamp.increase();
        deskLamp.increase();
        deskLamp.increase();
        deskLamp.increase();
        deskLamp.increase();
        deskLamp.decrease();
    }

}
