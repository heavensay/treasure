package com.helix.dict.bean;

import com.helix.dict.annotation.Dict;

/**
 * @author ljy
 * @date 2019/7/15 16:16
 */
public class Child extends Parent {
    private String countryText;

    @Override
    public String getCountryText() {
        return countryText;
    }

    @Override
    public void setCountryText(String countryText) {
        this.countryText = countryText;
    }
}
