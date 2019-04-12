package com.frontierapp.frontierapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.SlideshowLayoutBinding;
import com.frontierapp.frontierapp.view.ImageViewerActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private SlideshowLayoutBinding slideshowBinding;
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
        slideshowBinding = SlideshowLayoutBinding.inflate(inflater, container, false);

        View view = slideshowBinding.getRoot();
        slideshowBinding.setUrl(image_urls.get(position));
        slideshowBinding.setListener(new ClickListener(slideshowBinding));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }



    public class ClickListener implements View.OnClickListener{
        SlideshowLayoutBinding binding;

        public ClickListener(SlideshowLayoutBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            Intent imageViewIntent = new Intent(context, ImageViewerActivity.class);

            ArrayList<String> stringArrayList = (ArrayList<String>) image_urls;
            imageViewIntent.putStringArrayListExtra("URL", stringArrayList);
            imageViewIntent.putExtra("INDEX_URL", binding.getUrl());
            context.startActivity(imageViewIntent);
        }
    }
}
