package com.neoflex.telegram.bot.config;

import com.neoflex.telegram.bot.handler.BrokerTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);

    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public BrokerTelegramBot MySuperTelegramBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        BrokerTelegramBot mySuperTelegramBot = new BrokerTelegramBot(options);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);
        log.info("User name : {}, user token : {}, path : {}", botUserName, botToken, webHookPath);
        return mySuperTelegramBot;
    }

    public static Logger getLog() {
        return log;
    }

    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}
