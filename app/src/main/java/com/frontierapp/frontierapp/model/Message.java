package com.frontierapp.frontierapp.model;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private String message;
    private Date sent;
    private String sender_id;
    private Boolean read;
    private Date edited;


    public Message() {
    }

    public Message(String message, Date sent, String sender_id, Boolean read, Date edited) {
        this.message = message;
        this.sent = sent;
        this.sender_id = sender_id;
        this.read = read;
        this.edited = edited;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }
}
