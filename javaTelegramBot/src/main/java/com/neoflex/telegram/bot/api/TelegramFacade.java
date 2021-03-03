package com.neoflex.telegram.bot.api;

import com.neoflex.telegram.bot.cache.UserDataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class TelegramFacade {

    private static final String END = "END";
    private static final String START = "/start";
    private static final Logger log = LoggerFactory.getLogger(TelegramFacade.class);
    private static final String[] figis = {"BTC", "ETH", "BNB", "DOGE", "DOT", "ADA"};

    @Autowired
    private BotStateContext botStateContext;

    @Autowired
    private UserDataCache userDataCache;

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;
        Message message = update.getMessage();
        log.info("Message is : {}", message.getText());
        if (message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;
        if (inputMsg.toUpperCase().equals(END)) {
            botState = BotState.UNSUBSCRIBE;
        } else if (Arrays.asList(figis).contains(inputMsg.toUpperCase())) {
            botState = BotState.FIGI_GOTTEN;
        } else {
            botState = BotState.SAY_HELLO;
        }
        userDataCache.setUsersCurrentBotState(userId, botState);
        replyMessage = botStateContext.processInputMessage(botState, message);
        return replyMessage;
    }

}
