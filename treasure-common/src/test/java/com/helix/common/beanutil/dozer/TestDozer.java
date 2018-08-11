package com.helix.common.beanutil.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lijianyu
 * @Date: 2018/7/26 16:14
 */
public class TestDozer {

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


    /**
     * bean1.inner1,bean2.inner2类型不一致，但是变量名称一致，也会复制；
     */
    @Test
    public void simpleTest(){
        Mapper mapper = new DozerBeanMapper();
        Bean2 bean2 = mapper.map(bean1,Bean2.class);

        Assert.assertNotNull(bean2.getInner1());
        Assert.assertNotNull(bean2.getMoney());
        Assert.assertNotNull(bean2.getUser_name());
    }

    @Test
    public void simpleTest2(){
        Bean2 bean2 = DozerBeanMapperUtil.map(bean1,Bean2.class);

        Assert.assertNotNull(bean2.getInner1());
        Assert.assertNotNull(bean2.getMoney());
        Assert.assertNotNull(bean2.getUser_name());
    }

}
