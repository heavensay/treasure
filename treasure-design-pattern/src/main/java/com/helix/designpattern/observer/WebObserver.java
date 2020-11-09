package com.helix.designpattern.observer;

public class WebObserver implements Observer {

    private Subject subject = null;

    public WebObserver(Subject subject){
        this.subject = subject;
        subject.register(this);
    }

    @Override
    public void update(Subject subject, String info) {
        System.out.println(this.getClass().getName()+":"+info);
    }
}
