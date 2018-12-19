package com.frontierapp.frontierapp.viewmodel;

import android.app.DownloadManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Feed;
import com.frontierapp.frontierapp.repository.FeedRepository;
import com.google.firebase.firestore.Query;

public class FeedViewModel extends ViewModel {
    private MutableLiveData<Feed> feedMutableLiveData;
    private FeedRepository feedRepository = new FeedRepository();

    public void retrieveFeed(Query query){
        feedRepository.retrieveFeed(query);
        feedMutableLiveData = feedRepository.getFeed();
    }

    public MutableLiveData<Feed> getFeed(){
        return feedMutableLiveData;
    }
}
