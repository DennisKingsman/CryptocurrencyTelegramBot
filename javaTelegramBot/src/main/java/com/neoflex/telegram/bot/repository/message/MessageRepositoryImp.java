package com.neoflex.telegram.bot.repository.message;

import com.neoflex.telegram.bot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositoryImp implements MessageRepository {

    @Autowired
    private RedisTemplate<Long, String> messageRedisTemplate;

    @Override
    public void save(Message entity) {
        messageRedisTemplate.opsForValue().set(entity.getMessageId(), entity.getMessage());
    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public void deleteById(Long id) {
        messageRedisTemplate.delete(id);
    }

    @Override
    public Message getById(Long id) {
        String message = messageRedisTemplate.opsForValue().get(id);
        return new Message(id, message);
    }

}
