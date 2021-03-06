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

    /**
     * 快照表名(数据历史表)
     * @return
     */
    String snapshotTableName() default "";

    /**
     * 主键
     * @return
     */
    String objectIdName() default "id";
}
