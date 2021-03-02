package com.neoflex.telegram.bot.controller.broker;

import com.neoflex.telegram.bot.handler.BrokerTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@RestController
public class WebHookController {

    private final Logger logger = LoggerFactory.getLogger(WebHookController.class);

    @Autowired
    private BrokerTelegramBot telegramBot;

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(
            @RequestBody Update update
    ) {
        logger.info("tgBoot here: {}", !Objects.isNull(telegramBot));
        logger.info("Bot name: {}", telegramBot.getBotUsername());
        return telegramBot.onWebhookUpdateReceived(update);
    }

}
