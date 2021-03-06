package com.neoflex.telegram.bot.api.handler.give;

import com.neoflex.telegram.bot.api.BotState;
import com.neoflex.telegram.bot.api.InputMessageHandler;
import com.neoflex.telegram.bot.cache.UserDataCache;
import com.neoflex.telegram.bot.model.ChatResponce;
import com.neoflex.telegram.bot.service.ReplyMessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
public class FigiGottenHandler implements InputMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(FigiGottenHandler.class);
    private final String[] fields = {"open", "close", "low", "high", "openTime"};

    @Autowired
    private Jedis jedis;

    private UserDataCache userDataCache;

    public FigiGottenHandler(UserDataCache userDataCache,
                             ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
    }

    //figi ->
    @Override
    public SendMessage handle(Message message) {
        log.info("Handling FigiGottenHandler");
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FIGI_GOTTEN;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        ChatResponce figi = userDataCache.getFigi(userId);
        log.info("userAnswer is {}", usersAnswer);
        log.info("user figi now is {}", figi);
        //from redis

        List<String> fromRedis = jedis.hmget(usersAnswer.toUpperCase(), fields);
        SendMessage replyToUser = new SendMessage(chatId,
                "You have subscribed to the " + usersAnswer + "Ur data : " + fromRedis.toString());
        userDataCache.saveFigi(userId, new ChatResponce(chatId, usersAnswer));
        return replyToUser;
    }

}
