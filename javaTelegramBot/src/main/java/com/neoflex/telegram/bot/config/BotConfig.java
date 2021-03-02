package com.neoflex.telegram.bot.config;

import com.neoflex.telegram.bot.handler.BrokerTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String webHookPath;
    private String botUserName;
    private String botToken;

//    private DefaultBotOptions.ProxyType proxyType;
//    private String proxyHost;
//    private int proxyPort;

    @Bean
    public BrokerTelegramBot MySuperTelegramBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);
//
//        options.setProxyHost(proxyHost);
//        options.setProxyPort(proxyPort);
//        options.setProxyType(proxyType);

        BrokerTelegramBot mySuperTelegramBot = new BrokerTelegramBot(options);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);
        return mySuperTelegramBot;
    }

}
