package com.neoflex.telegram.bot.api.handler.give;

import com.neoflex.telegram.bot.api.BotState;
import com.neoflex.telegram.bot.api.InputMessageHandler;
import com.neoflex.telegram.bot.cache.UserDataCache;
import com.neoflex.telegram.bot.service.ReplyMessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import redis.clients.jedis.Jedis;

@Component
public class FigiGottenHandler implements InputMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(FigiGottenHandler.class);

    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    @Autowired
    private Jedis jedis;

    public FigiGottenHandler(UserDataCache userDataCache,
                             ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
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

    //ddoc user with crypt
    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        String figi = userDataCache.getFigi(userId);
        log.info("userAnswer is {}", usersAnswer);
        log.info("user figi now is {}", figi);
        //from redis
        SendMessage replyToUser = new SendMessage(chatId, "U GOT UR BITCKOIN");
        userDataCache.saveFigi(userId, usersAnswer);
        return replyToUser;
    }

}
