package com.helix.designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 观察者模式
 * 场景：由主题通知多个观察者，实现解耦，用于mq，事件通知等中
 *
 */
public class InfoSubjectImpl implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private String info = null;

    @Override
    public void register(Observer observer) {
        lock.lock();
        try {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void unregister(Observer observer) {
        lock.lock();
        try{
            if (observers.contains(observer)) {
                observers.remove(observer);
            }
        }finally {
            lock.unlock();
        }
    }

    public void notifyObserver(){
        observers.forEach(ob->{
            ob.update(this, info);
        });
    }

    public void newinfoArrve(String info){
        this.info = info;
        this.notifyObserver();
    }

    public static void main(String[] args){
        InfoSubjectImpl subject = new InfoSubjectImpl();
        WebObserver webObserver = new WebObserver(subject);

        subject.newinfoArrve("hello");

    }
}
