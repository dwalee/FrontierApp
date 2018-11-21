package com.frontierapp.frontierapp;

public class Messages {
    private String Message;
    private long Time;
    private Boolean Seen;


    public Messages(){

    }

    public Messages(String message, long time, Boolean seen) {
        this.Message = message;
        this.Time = time;
        this.Seen = seen;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public Boolean getSeen() {
        return Seen;
    }

    public void setSeen(Boolean seen) {
        Seen = seen;
    }
}
