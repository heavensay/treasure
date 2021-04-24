package com.helix.designpattern.command;

/**
 * @author lijianyu
 * @date 2021/4/24 下午6:24
 */
public class LightIncreaseCommand implements Command{
    private Light light;

    public LightIncreaseCommand(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.increaseLightDeep();
    }
}
