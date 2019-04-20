package com.helix.demo;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * string字符串当代码来动态执行
 *
 * java代码动态执行感觉不太方便，可以考虑嵌入lua，script代码来完成这样的逻辑
 *
 * @author lijianyu
 * @date 2019/3/28 15:22
 */
public class ExecuteJavaString {
    private String javaScript = "System.out.println(\"你好\")";


    @Test
    public void executeJavaScript() throws Exception{
        Class sys = Class.forName("java.lang.System");
        Field out = sys.getField("out");
        System.out.println(out);
    }

    @Test
    public void executeJavaScriptByJexl() throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("out",System.out);

        JexlEngine jexl=new JexlEngine();
        Expression e = jexl.createExpression(javaScript);
        JexlContext jc = new MapContext();
        for(String key:map.keySet()){
            jc.set(key, map.get(key));
        }
        System.out.println(e.evaluate(jc));
    }

}
