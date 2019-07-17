package com.helix.dict.spring.dict.bodyadvice.autoconfigure;

import com.helix.dict.BaseConfigTest;
import com.helix.dict.EnumDictSourceAutoCollect;
import com.helix.dict.spring.dict.bodyadvice.DictMapperResponseBodyAdvice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 *
 * 需要web环境，才能生成DictMapperAutoConfiguration
 * @author ljy
 * @date 2019/7/17 17:28
 */
public class DictMapperAutoConfigurationTest extends BaseConfigTest{

    @Autowired
    private DictMapperResponseBodyAdvice dictMapperResponseBodyAdvice;

    @Autowired
    private DictMapperAutoConfiguration dictMapperAutoConfiguration;

    @Autowired
    private EnumDictSourceAutoCollect enumDictSourceAutoCollect;

    @Test
    public void enumDictSourceAutoCollect() {
        Assert.assertNotNull(enumDictSourceAutoCollect);
        //在junit单元测试下面，main class路径为com.intellij.rt.execution.junit；
        //实际运行，是@SpringBootApplication注解对应类的路径，既项目路径
        System.out.println(enumDictSourceAutoCollect.getPackagePath());
    }
}