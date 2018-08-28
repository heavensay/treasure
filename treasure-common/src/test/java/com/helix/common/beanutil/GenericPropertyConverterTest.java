package com.helix.common.beanutil;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lijianyu
 * @date 2018/8/28 17:30
 */
public class GenericPropertyConverterTest {

    @Test
    public void convertObject() {
        UnderLineBean underLineBean = new UnderLineBean();
        underLineBean.setCountry_name("china");

        CamelBean cb = GenericPropertyConverter.convertObject(underLineBean,CamelBean.class, GenericPropertyConverter.PropertyNameStyle.UNDERLINE);
        Assert.assertEquals("china",cb.getCountryName());

    }
}