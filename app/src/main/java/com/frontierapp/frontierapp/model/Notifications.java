package com.frontierapp.frontierapp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Notifications extends ArrayList<Notification> implements Comparator<Notification> {

    public void reverseSort(){
        Collections.sort(this, this);
    }

    @Override
    public int compare(Notification first, Notification second) {
        return (int) ( -1 * (first.getUpdated().getTime() - second.getUpdated().getTime()));
    }
}
