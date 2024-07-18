package com.harsh.interconnect.models;

public class ChatModel {
    String message="";
    String senderUID = "";
    String dateTime = "";
    public ChatModel(String message, String senderUID, String dateTime){
        this.message = message;
        this.senderUID = senderUID;
        this.dateTime = dateTime;
    }
    public String getMessage() {
        return message;
    }
    public String getSenderUID() {
        return senderUID;
    }
    public String getDateTime() {
        return dateTime;
    }
}
