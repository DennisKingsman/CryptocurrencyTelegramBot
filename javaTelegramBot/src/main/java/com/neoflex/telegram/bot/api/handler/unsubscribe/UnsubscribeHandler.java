package com.neoflex.telegram.bot.api.handler.unsubscribe;

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

@Component
public class UnsubscribeHandler implements InputMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(UnsubscribeHandler.class);

    @Autowired
    private UserDataCache userDataCache;
    @Autowired
    private ReplyMessagesService messagesService;

    @Override
    public SendMessage handle(Message message) {
        log.info("Handling UnsubscribeHandler");
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.UNSUBSCRIBE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        log.info("to SayHelloHandler");
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askFigi");
        // did user enter some figi?
        // we'll assuming that yap
        userDataCache.setUsersCurrentBotState(userId, BotState.SAY_HELLO);
        return replyToUser;
    }

}
