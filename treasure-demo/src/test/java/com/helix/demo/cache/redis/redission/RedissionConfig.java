package com.helix.demo.cache.redis.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author lijianyu
 * @date 2023/9/8 11:18
 */
public class RedissionConfig {
    private static RedissonClient redissonClient;
    static {
        Config config = new Config();
        config.useSingleServer()
//                .setAddress("redis://127.0.0.1:6379")
                .setAddress("redis://127.0.0.1:6379")
//                .setPassword("123456")
                .setDatabase(0);
        //获取客户端
        redissonClient = Redisson.create(config);
    }
//    private void cofig(){
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://127.0.0.1:6379")
//                .setPassword("123456")
//                .setDatabase(0);
//        //获取客户端
//        redissonClient = Redisson.create(config);
////        //获取所有的key
////        redissonClient.getKeys().getKeys().forEach(key -> System.out.println(key));
////        //关闭客户端
////        redissonClient.shutdown();
//    }

    public static RedissonClient getRedissonClient(){
        return redissonClient;
    }
}
