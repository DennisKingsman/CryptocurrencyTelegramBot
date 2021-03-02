package com.neoflex.telegram.bot.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/redis")
public class RedisController {

    @Autowired
    private Jedis jedis;

    @GetMapping("/post/{key}")
    public List<String> post(@PathVariable("key") final String key) {
        String[] fields = {"open", "close", "low", "high", "openTime"};
        String[] vals = {"1", "2", "3", "4", "5"};
        Map<String, String> post = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            post.put(fields[i], vals[i]);
        }
        jedis.hmset(key, post);
        return jedis.hmget(key, fields);
    }

}
