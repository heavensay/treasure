package com.helix.dict.introspect;

import com.alibaba.fastjson.JSON;
import com.helix.dict.annotation.Dict;
import com.helix.dict.bean.CountryEnum;
import com.helix.dict.bean.HelloBean;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ljy
 * @date 2019/7/15 10:44
 */
public class DictBeanIntrospectorTest {
    @Test
    public void acquireDictMetadata() {
        System.out.println(JSON.toJSONString(DictBeanIntrospector.acquireDictMetadata(HelloBean.class)));

        Assert.assertTrue(DictBeanIntrospector.acquireDictMetadata(HelloBean.class).size() >0);
    }

    @Test
    public void listDictMetadataTest() {
        List<DictMetadata> metadatas = DictBeanIntrospector.acquireDictMetadata(CustomList.class);
        Assert.assertTrue(metadatas.size() == 1);
    }

    class CustomList extends ArrayList{
        private String country;
        @Dict(type = CountryEnum.class, valueFieldName = "country")
        private String countryText;

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
    }
}