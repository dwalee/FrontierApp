package com.frontierapp.frontierapp;

public class chatInfo {
    String recipient;
    String sender;
    String message;

    public void chatInfo(){

    }

    public void chatInfo(String recipient, String sender, String message){
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
