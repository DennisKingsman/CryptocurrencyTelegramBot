package com.neoflex.telegram.bot.cache;

import com.neoflex.telegram.bot.api.BotState;

public interface DataCache {

    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    String getFigi(int userId);

    void saveFigi(int userId, String figi);

}
