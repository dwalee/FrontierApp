package com.frontierapp.frontierapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frontierapp.frontierapp.databinding.SlideshowImageviewLayoutBinding;

import java.util.List;

public class SlideshowImageViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    public SlideshowImageviewLayoutBinding slideshowBinding;
    private List<String> image_urls;

    public SlideshowImageViewAdapter(Context context, List<String> image_urls) {
        this.context = context;
        this.image_urls = image_urls;
    }

    @Override
    public int getCount() {
        return image_urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return (view==(RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        slideshowBinding = SlideshowImageviewLayoutBinding.inflate(inflater, container, false);

        View view = slideshowBinding.getRoot();
        slideshowBinding.setUrl(image_urls.get(position));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }


}