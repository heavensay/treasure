package com.helix.demo.java.lang.annotation;

@Role("admin")
@Role("manager")
//@Roles(value = {@Role("admin"),@Role("manager")})  //jdk8之前写法
public class User {
}
