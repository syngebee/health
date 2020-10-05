package com.itheima.health.utils.redis;


import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 *    用原始的RedisTemplate 代码插入到redis，会产生乱码，但是用代码去查询是没有问题的，
 *    客户端查看就会出现乱码线下，直接在redis客户端操作是有问题的，所以我们都会在config包下面来设置RedisTemplate的序列化
 */
@Configuration
public class RedisConfig {



    @Bean  //  改写 redistemplate序列化机制  采用fastJson提供的序列化机制
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        System.out.println("redisTemplate---------in new .==========");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer); //   存储 字符串 也可以 存储对象  性能比较高
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }



}