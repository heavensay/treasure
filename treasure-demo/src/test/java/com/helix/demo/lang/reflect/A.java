package com.helix.demo.lang.reflect;

import java.util.List;

public class A<T> {
    private T source;

    public A(T str) {
        this.source = str;
    }

    public T getSource(T t) {
        return (T) source;
    }

    public void getList(List<String> list) {

    }
}