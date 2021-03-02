package com.neoflex.telegram.bot.repository.user;

import com.neoflex.telegram.bot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String USER_KEY = "USER";

    @Autowired
    private HashOperations<String, String, User> hashOperations;

    @Override
    public void save(User user) {
        hashOperations.put(USER_KEY, user.getUserId(), user);
    }

    @Override
    public Map<String, User> findAll() {
        Map<String, User> users = hashOperations.entries(USER_KEY);
        log.info("Did it find users : {}", Objects.isNull(users));
        return users;
    }

    @Override
    public User findById(String id) {
        return hashOperations.get("USER", id);
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
