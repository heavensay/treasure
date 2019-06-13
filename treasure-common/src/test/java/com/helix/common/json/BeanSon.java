package com.helix.common.json;

/**
 * @author ljy
 * @date 2019/6/12 20:40
 */
public class BeanSon<T> {

    private String label;

    private T result;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
