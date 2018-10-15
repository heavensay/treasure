package com.helix.common.freemarker;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijianyu
 * @date 2018/10/14 19:35
 */
public class FreemarkerConfigurationTest {

    /**
     * bean类需public修饰，才能被freemarker处理；
     * @throws Exception
     */
    @Test
    public void processTemplateFile() throws Exception{
        StringWriter sw = new StringWriter();
        Map map = new HashMap();
        map.put("brand","雅马哈");
        map.put("openDate","2018-10-15");

//        Bean bean = new Bean();
//        bean.setBrand("雅马哈");
//        bean.setOpenDate("2018-10-15");
        FreemarkerConfiguration.processTemplateFile("agreement.ftl",map,sw);
        Assert.assertNotNull(sw.toString());
        System.out.println(sw.toString());
        sw.close();
    }
}

