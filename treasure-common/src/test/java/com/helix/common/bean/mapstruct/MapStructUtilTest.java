package com.helix.common.bean.mapstruct;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lijianyu
 * @date 2018/8/11 16:57
 */
public class MapStructUtilTest {

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

        List<Long> nums = Arrays.asList(1L, 2L);
        bean1.setToUids(nums);
    }

    @Test
    public void map() {
        Bean2 bean2 = MapStructUtil.INSTANCE.toBean2(bean1);

        Assert.assertNotNull(bean2.getInner1());
        Assert.assertNotNull(bean2.getToUids());
        Assert.assertNotNull(bean2.getMoney());
    }


}