package com.frontierapp.frontierapp.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frontierapp.frontierapp.R;

public class CustomChatBindingAdapter {

    @BindingAdapter("bind:format")
    public static void setFormat(TextView view, Boolean b){
        RelativeLayout.LayoutParams params;
        Drawable myDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.my_message_background);
        Drawable theirDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.their_message_background);
        if (b) {
            params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            view.setBackground(myDrawable);
            //view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            params.setMargins(200,10, 10, 10);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            view.setLayoutParams(params);
        } else {
            params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            view.setBackground(theirDrawable);
            view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.senderImageView);
            params.setMargins(10,10,10,10);
            view.setLayoutParams(params);
        }
    }


}
