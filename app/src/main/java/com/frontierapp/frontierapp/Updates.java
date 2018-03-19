package com.frontierapp.frontierapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Updates {

    public String Uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();


    public Updates() {

    }

    public Updates(String Uid, String author, String title, String body) {
            this.Uid = Uid;
            this.author = author;
            this.title = title;
            this.body = body;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", Uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }

}
