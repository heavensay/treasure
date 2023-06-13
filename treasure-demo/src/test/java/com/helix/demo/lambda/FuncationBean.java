package com.helix.demo.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 实现Serializable，为了获取lambda方法信息
 * @author lijianyu
 * @date 2023/6/12 17:41
 */
@FunctionalInterface
public interface FuncationBean<T, R> extends Function<T, R>,Serializable {

}
