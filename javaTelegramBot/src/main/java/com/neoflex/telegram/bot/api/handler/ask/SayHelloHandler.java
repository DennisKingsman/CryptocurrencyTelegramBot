package com.neoflex.telegram.bot.api.handler.ask;

import com.neoflex.telegram.bot.api.BotState;
import com.neoflex.telegram.bot.api.InputMessageHandler;
import com.neoflex.telegram.bot.cache.UserDataCache;
import com.neoflex.telegram.bot.service.ReplyMessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SayHelloHandler implements InputMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(SayHelloHandler.class);

    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public SayHelloHandler(UserDataCache userDataCache,
                           ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        log.info("Handling SayHelloHandler");
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SAY_HELLO;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askFigi");
        // did user enter some figi?
        // we'll assuming that yap
        userDataCache.setUsersCurrentBotState(userId, BotState.FIGI_GOTTEN);
        return replyToUser;
    }
}
