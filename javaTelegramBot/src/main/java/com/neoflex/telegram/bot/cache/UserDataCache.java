package com.neoflex.telegram.bot.cache;

import com.neoflex.telegram.bot.api.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class UserDataCache implements DataCache {

    private Map<Integer, BotState> usersBotStates = new HashMap<>();
    //todo Map<Integer, List<String>>
    private Map<Integer, String> usersFigiData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.SAY_HELLO;
        }
        return botState;
    }

    @Override
    public String getFigi(int userId) {
        String figi = usersFigiData.get(userId);
        if (Objects.isNull(figi)) {
            figi = "";
        }
        return figi;
    }

    @Override
    public void saveFigi(int userId, String figi) {
        usersFigiData.put(userId, figi);
    }

}
