package com.helix.designpattern.command;

/**
 * @author lijianyu
 * @date 2021/4/24 下午6:17
 */
public class PowerChangeCommand implements Command {
    private Power power = null;

    public PowerChangeCommand(Power power){
        this.power = power;
    }

    @Override
    public void execute() {
        power.onOrOff();
    }
}
