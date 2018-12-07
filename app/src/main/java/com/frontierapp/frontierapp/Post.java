package com.frontierapp.frontierapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
    private String post_id;
    private String post_title;
    private String post_desc;
    private Date created, updated;
    private List<String> image_urls;
    private String type;
    private String posted_by, posted_by_id;
    private String profile_url;
    private double count; //Number of users who hearted, rated, or voted on a post
    private double positive_value; //The amount of hearts on a post, total amount for rating, or number of upvotes
    private double negative_value; //number of downvotes
    private double comment_count;

    public Post() {

    }

    public Post(String post_id, String post_title, String post_desc, Date created, Date updated,
                List<String> image_urls, String type, String posted_by, String posted_by_id, String profile_url,
                double count, double positive_value, double negative_value, double comment_count) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.post_desc = post_desc;
        this.created = created;
        this.updated = updated;
        this.image_urls = image_urls;
        this.type = type;
        this.posted_by = posted_by;
        this.posted_by_id = posted_by_id;
        this.profile_url = profile_url;
        this.count = count;
        this.positive_value = positive_value;
        this.negative_value = negative_value;
        this.comment_count = comment_count;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

    @ServerTimestamp
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @ServerTimestamp
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getPositive_value() {
        return positive_value;
    }

    public void setPositive_value(double positive_value) {
        this.positive_value = positive_value;
    }

    public double getNegative_value() {
        return negative_value;
    }

    public void setNegative_value(double negative_value) {
        this.negative_value = negative_value;
    }

    public String getPosted_by_id() {
        return posted_by_id;
    }

    public void setPosted_by_id(String posted_by_id) {
        this.posted_by_id = posted_by_id;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public double getComment_count() {
        return comment_count;
    }

    public void setComment_count(double comment_count) {
        this.comment_count = comment_count;
    }
}
