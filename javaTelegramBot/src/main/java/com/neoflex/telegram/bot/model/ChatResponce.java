package com.neoflex.telegram.bot.model;

import java.util.Objects;

public class ChatResponce {

    private long chatId;
    private String figi;

    public ChatResponce() {
    }

    public ChatResponce(long chatId, String figi) {
        this.chatId = chatId;
        this.figi = figi;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResponce that = (ChatResponce) o;
        return chatId == that.chatId &&
                Objects.equals(figi, that.figi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, figi);
    }

}
