package com.frontierapp.frontierapp.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Feed extends ArrayList<Post> implements Comparator<Post> {
    public static void sort(){

    }

    @Override
    public int compare(Post post, Post t1) {
        return (int) (-1 * post.getCreated().getTime() - t1.getCreated().getTime());
    }
}
