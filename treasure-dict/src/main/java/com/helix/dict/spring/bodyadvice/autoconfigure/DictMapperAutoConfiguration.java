package com.helix.dict.spring.bodyadvice.autoconfigure;

import com.helix.dict.spring.bodyadvice.DictMapperResponseBodyAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ljy
 * @date 2019/7/15 20:43
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "helix.enhance.dict.mapping",name = "enabled",havingValue = "true")
public class DictMapperAutoConfiguration {

    @Bean
    public DictMapperResponseBodyAdvice dictMapperResponseBodyAdvice(){
        DictMapperResponseBodyAdvice dictMapperResponseBodyAdvice = new DictMapperResponseBodyAdvice();
        return dictMapperResponseBodyAdvice;
    }

}
