package com.example.live_chat_android;

public class instantMessage {
    private String authorName;
    private String message;

    public instantMessage(String authorName, String message) {
        this.authorName = authorName;
        this.message = message;
    }

    public instantMessage() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getMessage() {
        return message;
    }
}
