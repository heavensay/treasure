package com.helix.dict.annotation;

import com.helix.dict.DefaultType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供pojo上需要翻译的属性的元数据
 * e.g. :
 *  type:User.class(建议表对应的实体class)
 *  category:User(建议表名)
 *  code:gender(建议表中字典字段)
 *
 * @author ljy
 * @date 2019/7/3 17:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    Class type() default DefaultType.class;

    String category() default DictConfiguration.DEFAULT_CATEGORY;

    String code() default DictConfiguration.DEFAULT_CODE;

    /**
     * value字段名，从中获取值
     * @return
     */
    String valueFieldName();
}
