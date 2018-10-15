package com.helix.common.freemarker;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author lijianyu
 * @date 2018/10/15 16:50
 */
public class FreemarkerParseTest {

    @Test
    public void processContent() {
    }

    @Test
    public void getProcessedContent() throws Exception{
        Map map = new HashMap();
        String result = FreemarkerParse.getProcessedContent("map:name:${name!},age:${age!}",map);
        Assert.assertNotNull(result);

        System.out.println(result);
    }

}