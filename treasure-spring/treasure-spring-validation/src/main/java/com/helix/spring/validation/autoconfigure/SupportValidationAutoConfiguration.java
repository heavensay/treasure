package com.helix.spring.validation.autoconfigure;

import com.helix.spring.validation.SupportValidatedMethodPostProcessor;
import org.hibernate.validator.internal.engine.ConfigurationImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;

/**
 * @author ljy
 * @date 2019/5/10 17:13
 */
@Configuration
@ConditionalOnClass(ExecutableValidator.class)
@ConditionalOnResource(resources = "classpath:META-INF/services/javax.validation.spi.ValidationProvider")
@AutoConfigureBefore(value = ValidationAutoConfiguration.class)
@ConditionalOnProperty(prefix = "helix.enhance.validation",name = "enabled",havingValue = "true")
//@Import(PrimaryDefaultValidatorPostProcessor.class)
public class SupportValidationAutoConfiguration {

    /**
     * 配置Validaiton，使用国际化配置文件来展示验证信息
     * @param messageSource
     * @return
     */
    @Bean
    public Validator validator(MessageSource messageSource){
        //使用国际化消息文件来返回验证信息
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean(){
            @Override
            protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
                ((ConfigurationImpl)configuration).allowOverridingMethodAlterParameterConstraint(true);
            }
        };
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        validator.setMessageInterpolator(interpolatorFactory.getObject());
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    @ConditionalOnMissingBean
    public static MethodValidationPostProcessor methodValidationPostProcessor(
            Environment environment, @Lazy Validator validator) {
        SupportValidatedMethodPostProcessor processor = new SupportValidatedMethodPostProcessor();
        boolean proxyTargetClass = environment
                .getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return processor;
    }


}
