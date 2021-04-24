package com.helix.designpattern.command;

/**
 * 减少光亮
 * @author lijianyu
 * @date 2021/4/24 下午6:24
 */
public class LightDecreaseCommand implements Command{
    private Light light;

    public LightDecreaseCommand(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.decreaseLightDeep();
    }
}
