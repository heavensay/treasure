package com.helix.designpattern.proxy.inject;

public class DemoProxy {

    @InjectAnnotation(value = "before")
    public void beProxyMethod() {
        System.out.println(" it is the main invacation method");
    }
}
