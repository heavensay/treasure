package com.helix.datatrail.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lijianyu
 * @date 2019/1/2 11:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrailTable {

    String tableName();

    /**
     * 主键
     * @return
     */
    String identifyName() default "id";

    /**
     * 搜索使用的主键
     * @return
     */
    String searchIdName() default "";
}
