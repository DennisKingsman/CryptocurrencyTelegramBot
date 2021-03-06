package com.neoflex.telegram.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Bean
    Jedis jedis() {
        return new Jedis("94.180.35.212", 6379);
    }

}
