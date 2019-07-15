package com.helix.dict;

import com.helix.dict.annotation.DictConfiguration;
import com.helix.dict.bean.*;
import com.helix.dict.source.EnumDictSourceCollect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ljy
 * @date 2019/7/15 11:25
 */
public class SysDictManagerTest {

    HelloBean helloBean = null;
    HelloBean helloBean2 = null;

    @Before
    public void beanInit(){
        //字典数据源注册
        EnumDictSourceCollect.INSTANCE.loadEnumData(CountryEnum.class);
        EnumDictSourceCollect.INSTANCE.loadEnumData(HelloEnum.class);
        EnumDictSourceCollect.INSTANCE.loadEnumData(DigitEnum.class);
        EnumDictSourceCollect.INSTANCE.loadEnumData(AnimalEnum.class);

        helloBean = new HelloBean();
        helloBean.setName("susan");
        helloBean.setAnimalName("DOG");
        helloBean.setDigit(1);
        helloBean.setCountry("zh");

        helloBean2 = new HelloBean();
        helloBean2.setName("tom");
        helloBean2.setAnimalName("CAT");
        helloBean2.setDigit(2);
        helloBean2.setCountry("am");
    }

    /**
     * 测试基本Bean的字典转换
     */
    @Test
    public void mappingForBean() {
        SysDictManager.mapping(helloBean);
        Assert.assertNotNull(helloBean.getCountryText());
        Assert.assertNotNull(helloBean.getDigitText());
    }

    /**
     * 测试父子类中，转换是否正常
     * Note：父子都有相同的属性，以子类的为准
     */
    @Test
    public void mappingForBeanByExtend() {
        Child child = new Child();
        child.setCountry("zh");
        child.setDigit(2);
        SysDictManager.mapping(child);
        Assert.assertNotNull(child.getCountryText());
        Assert.assertNotNull(child.getDigitText());
    }

    /**
     * 测试List类型，字典转换
     */
    @Test
    public void mappingForList() {
        List list = new ArrayList();
        list.add(helloBean);
        list.add(helloBean2);

        SysDictManager.mapping(list);
        Assert.assertNotNull(helloBean.getCountryText());
        Assert.assertNotNull(helloBean.getDigitText());
        Assert.assertNotNull(helloBean2.getCountryText());
        Assert.assertNotNull(helloBean2.getDigitText());
    }


    /**
     * 测试Map类型，字典转换
     */
    @Test
    public void mappingForMap() {
        Map map = new HashMap();
        map.put("hellobean",helloBean);
        map.put("hellobean2",helloBean2);

        SysDictManager.mapping(map);
        Assert.assertNotNull(helloBean.getCountryText());
        Assert.assertNotNull(helloBean.getDigitText());
        Assert.assertNotNull(helloBean2.getCountryText());
        Assert.assertNotNull(helloBean2.getDigitText());
    }

    @Test
    public void containKey() {
        DictKey dictKey = new DictKey(HelloEnum.class, DictConfiguration.DEFAULT_CATEGORY,DictConfiguration.DEFAULT_CODE,"A");
        Assert.assertTrue(SysDictManager.containKey(dictKey));
    }

    @Test
    public void get() {
        DictKey dictKey = new DictKey(HelloEnum.class, DictConfiguration.DEFAULT_CATEGORY,DictConfiguration.DEFAULT_CODE,"A");
        Object valueLabel = SysDictManager.get(dictKey);
        Assert.assertNotNull(valueLabel);
    }
}
