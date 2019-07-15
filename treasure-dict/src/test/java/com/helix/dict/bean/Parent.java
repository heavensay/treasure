package com.helix.dict.bean;

import com.helix.dict.annotation.Dict;

/**
 * @author ljy
 * @date 2019/7/15 16:16
 */
public class Parent {

    private String name;

    private String country;

    @Dict(type = CountryEnum.class,valueColumn = "country")
    private String countryText;

    private Integer digit;

    @Dict(type = DigitEnum.class,valueColumn = "digit")
    private String digitText;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryText() {
        return countryText;
    }

    public void setCountryText(String countryText) {
        this.countryText = countryText;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public String getDigitText() {
        return digitText;
    }

    public void setDigitText(String digitText) {
        this.digitText = digitText;
    }
}
