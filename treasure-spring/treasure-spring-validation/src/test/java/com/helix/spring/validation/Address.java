package com.helix.spring.validation;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ljy
 * @date 2019/4/18 11:14
 */
public class Address {

    @NotNull(message = "街道不能为空")
    private String street;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
