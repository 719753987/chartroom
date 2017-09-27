package com.chartroom.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by icinfo on 2017-09-06.
 */
public class RedisInit {
    /*private static Jedis jedis;

    private final static String HOST = "192.168.13.128";

    private final static int PORT = 6379;

    private final static int TIMEOUT = 10000;

    private final static String PASSWORD = "a1385876";

    public static synchronized Jedis getInstance(){
        if(jedis == null){
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            JedisPool jedisPool = new JedisPool(jedisPoolConfig,HOST,PORT,TIMEOUT,PASSWORD);
            jedis = jedisPool.getResource();
        }
        return jedis;
    }

    public static synchronized void closeJedis(){
        if(jedis != null)
            jedis.close();
    }

    public static void main(String[] args){
        Jedis jedis = RedisInit.getInstance();
        jedis.set("onlineCount","0");
        RedisInit.closeJedis();
    }*/
}
