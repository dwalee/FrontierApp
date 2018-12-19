package com.frontierapp.frontierapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.frontierapp.frontierapp.R;

import java.util.List;

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    LayoutInflater inflater;
    private List<String> image_urls;

    public SlideShowAdapter(Context context, List<String> image_urls) {
        this.context = context;
        this.image_urls = image_urls;
    }

    @Override
    public int getCount() {
        return image_urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slideshow_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.postImageView);

        Glide.with(view)
                .load(image_urls.get(position))
                .into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
