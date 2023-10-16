package com.helix.demo.cache.redis.redission;

import org.junit.Test;
import org.redisson.api.MapOptions;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.map.MapLoader;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lijianyu
 * @date 2023/9/8 11:22
 */
public class RedissionTest {

    /**
     * string操作
     */
    @Test
    public void testString(){
        RBucket<Object> key1 = RedissionConfig.getRedissonClient().getBucket("key1");
        boolean b = key1.trySet("123");
        System.out.println(b);
        key1.set("333");
        System.out.println(b);
        System.out.println(key1.get());

        // 设置value和key的有效期
//        key1.set("张三", 30, TimeUnit.SECONDS);
    }

    /**
     * map结构操作
     */
    @Test
    public void testMapSimple(){
        RMap<String, Object> map1 = RedissionConfig.getRedissonClient().getMap("map1");
        map1.put("id", 1L);
        map1.put("name","tom");
        map1.expire(30, TimeUnit.SECONDS);
        System.out.println(RedissionConfig.getRedissonClient().getMap("map1").get("id"));
    }


    /**
     * 获取多个key，没设置的key就不会返回值了；
     */
    @Test
    public void testLoader(){
        MapOptions<Object, Object> loader = MapOptions.defaults().loader(new MapLoader<Object, Object>() {
            @Override
            public Object load(Object key) {
                return "dddd";
            }

            @Override
            public Iterable<Object> loadAllKeys() {
//                List list = new ArrayList();
//                return list;
                return null;
            }
        });


        RMap<Object, Object> map1 = RedissionConfig.getRedissonClient().getMap("map1",loader);

        map1.put("id", 1L);
        map1.put("name","tom");
        map1.expire(60, TimeUnit.SECONDS);


        HashSet<String> set = new HashSet<String>() {{
            add("a");
            add("b");
            add("c");
            add("name");
        }};

        Map<String, Object> getMap = RedissionConfig.getRedissonClient()
                .<String, Object>getMap("map1")
                .getAll(set);
        getMap.keySet().stream().forEach(key -> {
            System.out.println(key+":"+getMap.get(key));
        });
    }
//    /**
//     * 位图记录用户在线状态
//     */
//    @Test
//    public void testBitMap(){
//        RBitSet bitmap = RedissionConfig.getRedissonClient().getBitSet("map1");
//        bitmap.set();
//        bitmap.expire(30, TimeUnit.SECONDS);
//        System.out.println(RedissionConfig.getRedissonClient().getMap("map1").get("id"));
//    }
}
