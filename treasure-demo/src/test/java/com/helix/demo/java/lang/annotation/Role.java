package com.helix.demo.java.lang.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Repeatable(value = Roles.class)
@Retention(RUNTIME)
public @interface Role {
    String value();
}


