package com.neoflex.telegram.bot.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1113799434508676115L;

    private Long messageId;
    private String message;

    public Message() {
    }

    public Message(Long messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
