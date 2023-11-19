package com.helix.demo.db.jedis.subandpub;

import com.helix.demo.db.jedis.RedisUtil;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisPublish {

    @Test
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.publish("im", "hello" + System.currentTimeMillis());
    }

}
