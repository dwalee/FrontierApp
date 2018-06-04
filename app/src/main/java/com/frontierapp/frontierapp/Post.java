package com.frontierapp.frontierapp;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    public String post_id;
    public String post_image_url;
    public String post_text;
    public Date post_timestamp;
    //public String author;

    /*public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();*/


    public Post() {

    }


    public Post(String post_id, String author, String title, String body) {
            this.post_id = post_id;
            //this.author = author;
            //this.title = title;
            //this.body = body;

    }

   /* @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", post_id);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }*/

}
