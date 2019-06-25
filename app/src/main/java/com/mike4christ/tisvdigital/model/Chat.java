package com.mike4christ.tisvdigital.model;

public class Chat {
    public String message;
    public String receiverEmail;
    public String receiver_firstname;
    public String senderEmail;
    public long timestamp;

    public Chat(){

    }

    public Chat(String receiver_firstname, String senderEmail, String receiverEmail, String message, long timestamp) {
        this.receiver_firstname = receiver_firstname;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.timestamp = timestamp;
    }
}
