package com.frontierapp.frontierapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.adapter.SlideShowAdapter;
import com.frontierapp.frontierapp.adapter.SlideshowImageViewAdapter;
import com.frontierapp.frontierapp.databinding.ActivityImageViewerBinding;
import com.frontierapp.frontierapp.databinding.ActivitySpaceBinding;

import java.util.List;

public class ImageViewerActivity extends AppCompatActivity {
    private ActivityImageViewerBinding imageViewerBinding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer);

        intent = getIntent();
        List<String> urls = intent.getStringArrayListExtra("URL");
        String url = intent.getStringExtra("INDEX_URL");
        int index = urls.indexOf(url);
        SlideshowImageViewAdapter adapter = new SlideshowImageViewAdapter(this,urls);
        imageViewerBinding.mediaViewPager.setAdapter(adapter);
        imageViewerBinding.mediaViewPager.setCurrentItem(index);
    }
}
