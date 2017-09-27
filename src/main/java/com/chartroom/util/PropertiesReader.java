package com.chartroom.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by icinfo on 2017-09-22.
 */
@Component
public class PropertiesReader {

    public static String redisHost;

    public static int redisPort;

    public static int redisTimeout;

    public static String redisPwd;

    public static String getRedisHost() {
        return redisHost;
    }
    @Value("${redis.host}")
    public void setRedisHost(String redisHost) {
        PropertiesReader.redisHost = redisHost;
    }

    public static int getRedisPort() {
        return redisPort;
    }
    @Value("${redis.port}")
    public void setRedisPort(int redisPort) {
        PropertiesReader.redisPort = redisPort;
    }

    public static int getRedisTimeout() {
        return redisTimeout;
    }
    @Value("${redis.timeout}")
    public void setRedisTimeout(int redisTimeout) {
        PropertiesReader.redisTimeout = redisTimeout;
    }

    public static String getRedisPwd() {
        return redisPwd;
    }
    @Value("${redis.password}")
    public void setRedisPwd(String redisPwd) {
        PropertiesReader.redisPwd = redisPwd;
    }
}
