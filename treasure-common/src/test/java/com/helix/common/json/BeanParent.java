package com.helix.common.json;

/**
 * @author ljy
 * @date 2019/6/12 20:40
 */
public class BeanParent<T> {

    private String name;

    private T data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
