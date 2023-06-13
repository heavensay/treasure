package com.helix.demo.lambda;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author lijianyu
 * @date 2023/6/13 18:24
 */
public class LambdaUtil {

    /**
     * 获取SerializedLambda，里面包含lambda的信息：入参方法名、类型、返回值；
     * todo 为了性能，需要使用缓存
     * @param fn
     * @return
     * @throws Exception
     */
    public static SerializedLambda getSerializedLambda(Function fn) throws Exception {
        //writeReplace改了好像会报异常
        Method write = fn.getClass().getDeclaredMethod("writeReplace");
        write.setAccessible(true);
        return (SerializedLambda) write.invoke(fn);
    }

}
