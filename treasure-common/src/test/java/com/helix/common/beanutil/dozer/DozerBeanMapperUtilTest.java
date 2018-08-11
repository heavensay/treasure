package com.helix.common.beanutil.dozer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lijianyu
 * @date 2018/8/11 16:57
 */
public class DozerBeanMapperUtilTest {

    Bean1 bean1;

    @Before
    public void initBean(){
        bean1 = new Bean1();
        bean1.setId(1);
        bean1.setContent("good");
        bean1.setMoney(new BigDecimal(5.3));
        bean1.setUserName("tom");
        Inner1 inner1 = new Inner1();
        inner1.setId(2);
        inner1.setInenerName("inner bean");
        bean1.setInner1(inner1);

        List<Inner1> list = new ArrayList<Inner1>();
        list.add(inner1);
        bean1.setInner1s(list);

    }

    @Test
    public void map() {
        Bean2 bean2 = DozerBeanMapperUtil.map(bean1,Bean2.class);

        Assert.assertNotNull(bean2.getInner1());
        Assert.assertNotNull(bean2.getMoney());
        Assert.assertNotNull(bean2.getUser_name());
    }
}