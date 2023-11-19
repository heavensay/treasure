package com.helix.demo.db.jedis.subandpub;

import com.helix.demo.db.jedis.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscribe {


    /**
     * 由于Jedis的subcribe操作是阻塞的
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        jedis.subscribe(new JedisPubSubListener(), "im");
    }

}

