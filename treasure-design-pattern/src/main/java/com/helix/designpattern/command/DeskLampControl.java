package com.helix.designpattern.command;

/**
 * 台灯控制器
 * 只要发出电源开关，灯源亮度增强/减少命令
 * @author lijianyu
 * @date 2021/4/24 下午6:08
 */
public class DeskLampControl {
    private Command onOrOffCommand;

    private Command increaseLightDeepCommand;

    private Command decreaseLightDeepCommand;


    public DeskLampControl(){
    }

    public void onOrOffLight(){
        onOrOffCommand.execute();
    }

    public void changeLightType(){

    }

    public void increase(){
        increaseLightDeepCommand.execute();
    }

    public void decrease(){
        decreaseLightDeepCommand.execute();
    }

    public void setOnorOffCommand(Command command){
        this.onOrOffCommand = command;
    }

    public void setIncreaseLightDeepCommand(Command command){
        this.increaseLightDeepCommand = command;
    }

    public void setDecreaseLightDeepCommand(Command command){
        this.decreaseLightDeepCommand = command;
    }
}
