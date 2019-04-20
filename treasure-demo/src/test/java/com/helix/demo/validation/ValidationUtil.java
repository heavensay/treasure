package com.helix.demo.validation;

import org.hibernate.validator.internal.engine.ConfigurationImpl;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashSet;
import java.util.Set;

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
            appendError(set, validateError);
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
     *
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

    private static <T> void appendError(Set<ConstraintViolation<T>> set, StringBuilder errorBuilder) {
        for (ConstraintViolation val : set) {
            errorBuilder.append(val.getMessage() + ";");
        }
    }

}
