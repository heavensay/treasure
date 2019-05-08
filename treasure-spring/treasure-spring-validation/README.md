# treasure-spring-validation
validation注解增强插件

## 说明
* springmvc默认开启的validation功能使用的是ServletModelAttributeMethodProcessor和MethodValidationPostProcessor进行validation
* MethodValidationPostProcessor:用于方法上参数的检验，对于@Validated注解的实体不检验，需要@Valid才检验；
* ServletModelAttributeMethodProcessor:在Controller层中，直接对实体进行validation校验，实体需要有@Validated或@Valid注释

## 功能
* SupportValidatedMethodInterceptor支持@Validated注解的实体进行检验
* SupportValidatedMethodInterceptor和SupportValidatedMethodPostProcessor:支持@Validated注解的实体进行检验，用于Service层dao层
等方法参数检验；
* ValidationUtil:编程式检验工具类

## 引用方式

```java
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
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    public SupportValidatedMethodPostProcessor supportValidatedMethodPostProcessor(Validator validator){
        SupportValidatedMethodPostProcessor supportValidatedMethodPostProcessor = new SupportValidatedMethodPostProcessor();
        supportValidatedMethodPostProcessor.setValidator(validator);

        return supportValidatedMethodPostProcessor;
    }
``` 