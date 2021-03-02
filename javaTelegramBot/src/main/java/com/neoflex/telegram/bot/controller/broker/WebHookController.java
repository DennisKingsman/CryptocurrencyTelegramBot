package com.neoflex.telegram.bot.controller.broker;

import com.neoflex.telegram.bot.handler.BrokerTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

    @Autowired
    private BrokerTelegramBot telegramBot;

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

}
