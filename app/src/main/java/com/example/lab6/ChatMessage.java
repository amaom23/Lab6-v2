package com.example.lab6;

public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean isSentButton;

    // Constructor
    public ChatMessage(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;

    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSent() { return isSentButton; }

}

