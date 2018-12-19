package com.frontierapp.frontierapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Messages extends ArrayList<Message> implements Comparator<Message>{

    public void sort(){
        Collections.sort(this, this);
    }


    @Override
    public int compare(Message oldMessage, Message newMessage) {
        return (int) (oldMessage.getSent().getTime() - newMessage.getSent().getTime());
    }
}
