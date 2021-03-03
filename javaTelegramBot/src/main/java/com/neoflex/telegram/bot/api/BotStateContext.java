package com.neoflex.telegram.bot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private static final Logger log = LoggerFactory.getLogger(BotStateContext.class);
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    //todo data here
    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    //of list of handlers
    // if some stmt then ... (figi gotten)
    private InputMessageHandler findMessageHandler(BotState currentState) {
        InputMessageHandler handler = messageHandlers.get(currentState);
        log.info("HandlerName is {}", handler.getHandlerName());
        return messageHandlers.get(currentState);
    }

}
