package com.helix.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供Enum的元数据，用于收集Enum字典数据
 * 只注释在Enum类
 * @author ljy
 * @date 2019/7/5 18:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictEnumSource {

    /*valueFieldName为空代表value为Enum名称*/
    String valueFieldName() default "";

    String valueLabelFieldName();
}
