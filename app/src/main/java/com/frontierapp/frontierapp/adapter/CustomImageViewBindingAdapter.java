package com.frontierapp.frontierapp.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CustomImageViewBindingAdapter {
    private static final String TAG = "CustomImageViewBindingA";

    @BindingAdapter("bind:image_url")
    public static void setUrl(ImageView view, String url){
        Log.d(TAG, "setUrl() called with: view = [" + view + "], url = [" + url + "]");
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter("bind:circle_image_url")
    public static void setCircleUrl(ImageView view, String url){
        Log.d(TAG, "setUrl() called with: view = [" + view + "], url = [" + url + "]");
        Glide.with(view.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

}
