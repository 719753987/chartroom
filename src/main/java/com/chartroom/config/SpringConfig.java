package com.chartroom.config;

import com.chartroom.util.PropertiesReader;
import com.chartroom.util.RedisInit;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by icinfo on 2017-09-21.
 */
@Configuration
@Import({WebSocketConfig.class})
@ComponentScan(basePackages = "com.chartroom")
public class SpringConfig {
    //属性配置文件
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer
                = new PropertyPlaceholderConfigurer();
        Resource jdbcResource = new ClassPathResource("jdbc.properties");
        Resource springResource = new ClassPathResource("spring.properties");
        propertyPlaceholderConfigurer.setLocations(jdbcResource,springResource);
        return propertyPlaceholderConfigurer;
    }

    //redis配置
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(PropertiesReader.redisHost);
        jedisConnectionFactory.setPort(PropertiesReader.redisPort);
        jedisConnectionFactory.setPassword(PropertiesReader.redisPwd);
        jedisConnectionFactory.setTimeout(PropertiesReader.redisTimeout);
        return jedisConnectionFactory;
    }
    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate =
                new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean
    public RedisInit redisInit(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,PropertiesReader.redisHost,PropertiesReader.redisPort
                ,PropertiesReader.redisTimeout,PropertiesReader.redisPwd);
        Jedis jedis = jedisPool.getResource();
        jedis.set("onlineCount","0");
        jedis.close();
        return new RedisInit();
    }
}
