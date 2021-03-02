package com.neoflex.telegram.bot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BrokerTelegramBot extends TelegramWebhookBot {

    private static final Logger log = LoggerFactory.getLogger(BrokerTelegramBot.class);

    private String webHookPath;
    private String botUserName;
    private String botToken;

    public BrokerTelegramBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    //changed long to String!
    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        log.info("User message is : {}", update.getMessage().getText());
        if (update.getMessage() != null && update.getMessage().hasText()) {
            try {
                execute(new SendMessage(
                        update.getMessage().getChatId().toString(),
                        "Hi " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

}
