package com.neoflex.telegram.bot.controller.message;

import com.neoflex.telegram.bot.model.Message;
import com.neoflex.telegram.bot.repository.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/add/{id}/{name}")
    public Message add(@PathVariable("id") final Long id,
                    @PathVariable("name") final String name) {
        messageRepository.save(new Message(id, name));
        return messageRepository.getById(id);
    }

}
