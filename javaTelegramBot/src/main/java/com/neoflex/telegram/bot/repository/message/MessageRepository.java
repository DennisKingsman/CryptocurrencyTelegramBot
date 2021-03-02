package com.neoflex.telegram.bot.repository.message;

import com.neoflex.telegram.bot.model.Message;
import com.neoflex.telegram.bot.repository.Repository;

public interface MessageRepository extends Repository<Message> {

    void deleteById(Long id);

    Message getById(Long id);

}
