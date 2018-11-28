package com.frontierapp.frontierapp.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomImageViewBindingAdapter {

    @BindingAdapter("bind:url")
    public static void setUrl(ImageView view, String url){
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

}
