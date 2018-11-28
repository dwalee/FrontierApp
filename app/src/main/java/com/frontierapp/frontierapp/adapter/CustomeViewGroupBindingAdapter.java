package com.frontierapp.frontierapp.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;

import com.frontierapp.frontierapp.R;

public class CustomeViewGroupBindingAdapter {
    @BindingAdapter("bind:background")
    public static void setBackground(RelativeLayout viewGroup, Boolean b){
        Drawable myDrawable = ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.my_message_background);
        Drawable theirDrawable = ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.their_message_background);
        if (b) {
            viewGroup.setBackground(myDrawable);
        } else {
            viewGroup.setBackground(theirDrawable);
        }
    }
}
