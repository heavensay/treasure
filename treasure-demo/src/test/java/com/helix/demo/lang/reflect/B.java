package com.helix.demo.lang.reflect;

import java.util.List;

/**
 * @author ljy
 * @date 2019/6/13 14:45
 */
public class B<T, M extends Number> {
    private T a;
    private String b;
    private List c;
    private List<String> d;
    private List<?> e;
    private List<? extends Number> f;
    private M g;
    private A<String> h;
    private A i;
}
