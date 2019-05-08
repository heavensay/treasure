package com.helix.demo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author ljy
 * @date 2019/5/7 16:50
 */
public class CaffeineTest {

    @Test
    public void simpleCache()throws Exception{
        Cache cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)//写入之后失效时间
                .removalListener((key,value,cause)->{//失效之后的回调
                    System.out.println("key:"+key+" value:"+value+" cause"+cause.name());
                })
                .build();

        cache.put("a","1");
        System.out.println("预期有值："+cache.getIfPresent("a"));
        Thread.sleep(3000);
        System.out.println("预期有值："+cache.getIfPresent("a"));
        Thread.sleep(3000);
        System.out.println("预期没值："+cache.getIfPresent("a"));

        Thread.sleep(5000);
    }

}
