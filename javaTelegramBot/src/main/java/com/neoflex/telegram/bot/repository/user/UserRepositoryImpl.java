package com.neoflex.telegram.bot.repository.user;

import com.neoflex.telegram.bot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private HashOperations<String, String, User> hashOperations;

    @Override
    public void save(User user) {
        hashOperations.put("USER", user.getUserId(), user);
    }

    @Override
    public Map<String, User> findAll() {
        return hashOperations.entries("USER");
    }

    @Override
    public User findById(String id) {
        return (User)hashOperations.get("USER", id);
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("USER", id);
    }

}
