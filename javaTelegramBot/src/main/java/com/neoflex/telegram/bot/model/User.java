package com.neoflex.telegram.bot.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String userId;
    private String userName;
    private String userMessage;

    public User() {
    }

    public User(String userId, String userName, String userMessage) {
        this.userId = userId;
        this.userName = userName;
        this.userMessage = userMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMessage='" + userMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userMessage, user.userMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userMessage);
    }

}
