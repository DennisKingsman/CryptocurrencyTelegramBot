package com.neoflex.telegram.bot.schedule;

import com.neoflex.telegram.bot.api.BotState;
import com.neoflex.telegram.bot.cache.UserDataCache;
import com.neoflex.telegram.bot.handler.BrokerTelegramBot;
import com.neoflex.telegram.bot.model.ChatResponce;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleFigi {

    private final Logger logger = LoggerFactory.getLogger(ScheduleFigi.class);
    private final String[] fields = {"open", "close", "low", "high", "openTime"};

    @Autowired
    private BrokerTelegramBot telegramBot;

    @Autowired
    private UserDataCache userDataCache;

    @Autowired
    private Jedis jedis;

    //todo url to bot
    @Scheduled(fixedRate = 5000)
    public void scheduledRedis() throws IOException, URISyntaxException {
        //NPE in cache
        Map<Integer, BotState> botState = userDataCache.getUsersBotStates();
        Map<Integer, ChatResponce> usersFigiData = userDataCache.getUsersFigiData();
        for (Map.Entry<Integer, BotState> e : botState.entrySet()) {
            if (e.getValue().equals(BotState.FIGI_GOTTEN)) {
                ChatResponce chatResponce = usersFigiData.get(e.getKey());
                String figi = chatResponce.getFigi();
                List<String> fromRedis = jedis.hmget(figi.toUpperCase(), fields);
                long chatId = chatResponce.getChatId();

                URIBuilder ub = new URIBuilder("https://api.telegram.org/bot"
                        + telegramBot.getBotToken()
                        + "/sendMessage");
                String join = String.join(" ", fromRedis);
                ub.addParameter("chat_id", String.valueOf(chatId) + "&");
                ub.addParameter("text", join);
                URL url = ub.build().toURL();
                logger.info("Url is : {}", url.getQuery());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
            }
        }
    }

}
