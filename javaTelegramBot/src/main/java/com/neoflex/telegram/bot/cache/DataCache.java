package com.neoflex.telegram.bot.cache;

import com.neoflex.telegram.bot.api.BotState;
import com.neoflex.telegram.bot.model.ChatResponce;

public interface DataCache {

    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    ChatResponce getFigi(int userId);

    void saveFigi(int userId, ChatResponce figi);

}
