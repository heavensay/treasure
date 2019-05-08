package com.helix.spring.validation;

import org.hibernate.validator.internal.engine.ConfigurationImpl;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 支持编程式进行validation校验
 */
public class ValidationUtil {

    private static ValidatorFactory vFactory;
    private static ExecutableValidator executableValidator;
    private static Validator validator;

    static {
        //下面方法指定message文件路径
//        Validator validator = Validation.byDefaultProvider()
//                .configure()
//                .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages.properties" )))
//                .buildValidatorFactory()
//                .getValidator();
        ConfigurationImpl configuration = ((ConfigurationImpl) Validation.byDefaultProvider().configure());
        configuration.allowOverridingMethodAlterParameterConstraint(true);
        configuration.allowMultipleCascadedValidationOnReturnValues(false);
        configuration.allowParallelMethodsDefineParameterConstraints(false);
        vFactory = configuration.buildValidatorFactory();
        executableValidator = vFactory.getValidator().forExecutables();
        validator = vFactory.getValidator();
    }

    /**
     * 实体类型校验
     *
     * @param t
     * @param groups
     * @param <T>
     * @throws ValidationException
     */
    public static <T> void validate(T t, Class<?>... groups) throws ValidationException {
        Set<ConstraintViolation<T>> set = validator.validate(t, groups);
        if (set.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            throw new ConstraintViolationException(set);
        }
    }

    /**
     * 方法中参数验证
     *
     * @param object
     * @param method
     * @param parameterValues
     * @param groups
     * @param <T>
     * @throws ConstraintViolationException
     */
    public static <T> void validateProperty(T object,
                                            Method method,
                                            Object[] parameterValues,
                                            Class<?>... groups) throws ValidationException {
        Set<ConstraintViolation<T>> set = executableValidator.validateParameters(object, method, parameterValues, groups);
        if (set.size() > 0) {
            throw new ConstraintViolationException(set);
        }
    }


    /**
     * 方法参数验证 兼容spring @validated注解验证
     * 支持分组验证
     * @param object
     * @param method
     * @param parameterValues
     * @param groups
     * @param <T>
     * @throws ConstraintDeclarationException
     */
    public static <T> void validatePropertyCompliable(T object,
                                                      Method method,
                                                      Object[] parameterValues,
                                                      Class<?>... groups) throws ValidationException {
        StringBuilder validateError = new StringBuilder();
        Set<ConstraintViolation<Object>> set = new LinkedHashSet<>();

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            Validated[] validated = parameter.getAnnotationsByType(Validated.class);
            if (validated != null && validated.length > 0) {
                Class<?>[] validatedGroupClasses = validated[0].value();
                if (parameterValues[i] != null) {
                    Set<ConstraintViolation<Object>> validatedSet = vFactory.getValidator().validate(parameterValues[i], groups);
                    set.addAll(validatedSet);
                }
            }
        }
        Set<ConstraintViolation<Object>> methodParameterSet = executableValidator.validateParameters(object, method, parameterValues, groups);
        set.addAll(methodParameterSet);

        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
    }

    /**
     * 支持方法上@Validated注释分组验证；以及方法参数@Validated注释的验证
     * 支持分组验证
     * @param <T>
     * @param object
     * @param method
     * @param parameterValues
     * @throws ConstraintDeclarationException
     */
    public static <T> void validatePropertyCompliable(T object,
                                                      Method method,
                                                      Object[] parameterValues) throws ValidationException {
        Validated methodAnnotation = method.getAnnotation(Validated.class);
        Class[] groups = new Class[0];
        if(methodAnnotation != null && methodAnnotation.value() != null){
            groups = methodAnnotation.value();
        }

        StringBuilder validateError = new StringBuilder();
        Set<ConstraintViolation<Object>> set = new LinkedHashSet<>();

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            Validated[] validated = parameter.getAnnotationsByType(Validated.class);
            if (validated != null && validated.length > 0) {
                Class<?>[] validatedGroupClasses = validated[0].value();
                if(groups.length>0){
                    //优先使用方法上validated的分组
                    validatedGroupClasses = groups;
                }

                if (parameterValues[i] != null) {
                    Set<ConstraintViolation<Object>> validatedSet = vFactory.getValidator().validate(parameterValues[i], validatedGroupClasses);
                    set.addAll(validatedSet);
                }
            }
        }
        Set<ConstraintViolation<Object>> methodParameterSet = executableValidator.validateParameters(object, method, parameterValues, groups);
        set.addAll(methodParameterSet);

        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
    }

    public static <T> String appendError(Set<ConstraintViolation<T>> set) {
        StringBuilder errorBuilder = new StringBuilder();
        for (ConstraintViolation val : set) {
            errorBuilder.append(val.getMessage() + ";");
        }

        return errorBuilder.subSequence(0,errorBuilder.length()).toString();
    }

}
