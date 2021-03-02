package com.neoflex.telegram.bot.config;

import com.neoflex.telegram.bot.handler.MessageSubscriber;
import com.neoflex.telegram.bot.model.Message;
import com.neoflex.telegram.bot.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class JedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisTemplate<String, User> redisTemplate() {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<Long, String> messageRedisTemplate() {
        RedisTemplate<Long, String> messageRedisTemplate = new RedisTemplate<>();
        messageRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return messageRedisTemplate;
    }

    @Bean
    HashOperations<String, String, User> hashOperations() {
        return redisTemplate().opsForHash();
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }

}
