package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Post {
    private String post_title;
    private String post_desc;
    private Date created, updated;
    private List<String> image_urls;
    private String type;
    private DocumentReference posted_by;
    private int positive_count;
    private int negative_count;
    private int comment_count;
    private DocumentReference user_ref;

    public Post() {

    }

    public Post(String post_title, String post_desc, Date created, Date updated,
                List<String> image_urls, String type, DocumentReference posted_by,
                int positive_count, int negative_count, int comment_count) {
        this.post_title = post_title;
        this.post_desc = post_desc;
        this.created = created;
        this.updated = updated;
        this.image_urls = image_urls;
        this.type = type;
        this.posted_by = posted_by;
        this.positive_count = positive_count;
        this.negative_count = negative_count;
        this.comment_count = comment_count;
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

    public DocumentReference getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(DocumentReference posted_by) {
        this.posted_by = posted_by;
    }

    public int getPositive_count() {
        return positive_count;
    }

    public void setPositive_count(int positive_count) {
        this.positive_count = positive_count;
    }

    public int getNegative_count() {
        return negative_count;
    }

    public void setNegative_count(int negative_count) {
        this.negative_count = negative_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
