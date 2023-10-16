package com.helix.common.bean.mapstruct;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean1,Bean2,里面都有private List<Long> toUids，并且bean上有泛型public class Bean1<F>编译就会报错：
 * java.lang.ClassCastException: java.lang.Long cannot be cast to com.helix.common.bean.mapstruct.Bean1
 *
 * 原因是：编译后的类，toUids使用了source2TargetList方法，很奇怪。去掉<F>就正常了。
 *
 * 但是实际上很多bean是有泛型的，也可以在source2TargetList上加@Named给个值，就正确了。具体什么编译错误还不清楚。先这样处理
 * @author lijianyu
 * @date 2023/10/15 17:42
 */
public class BeanMapperTest {
    Bean1 bean1;

    @Before
    public void initBean(){
        bean1 = new Bean1();
        bean1.setId(1);
        List<Long> nums = Arrays.asList(1L, 2L);
        bean1.setToUids(nums);
    }

    @Test
    public void convert(){
        Bean2 bean2 = BeanMapper.INSTANCE.source2Target(bean1);
        Assert.assertTrue(bean2.getToUids()!=null);
    }

    @Test
    public void convertList(){
        List<Bean1> list1 = new ArrayList<>();
        list1.add(bean1);
        List<Bean2> bean2 = BeanMapper.INSTANCE.source2TargetList(list1);
        Assert.assertTrue(bean2.size()>0);
    }

    @Test
    public void genericTest(){
//        Bean2 bean2 = BeanMapper.INSTANCE.map(bean1, Bean2.class);
//        Assert.assertTrue(bean2.getToUids()!=null);
    }
}
