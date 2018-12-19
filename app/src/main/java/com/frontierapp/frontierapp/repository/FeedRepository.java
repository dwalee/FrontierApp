package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Feed;
import com.frontierapp.frontierapp.model.Post;
import com.frontierapp.frontierapp.model.Profile;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class FeedRepository  implements OnSuccessCallback<Feed> {
    private MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
    private Firestore<Post> postFirestore;
    private FeedRepository.PostAsyncTask postAsyncTask;

    public void retrieveFeed(Query query) {
        postAsyncTask = new FeedRepository.PostAsyncTask();
        postFirestore = new Firestore(query);
        postAsyncTask.execute(this);
    }

    public MutableLiveData<Feed> getFeed() {
        return feedMutableLiveData;
    }

    @Override
    public void OnSuccess(Feed feed) {
        feed.sort();
        feedMutableLiveData.setValue(feed);
    }

    private class PostAsyncTask extends AsyncTask<OnSuccessCallback<Feed>, Void, Void> {
        private static final String TAG = "PostAsyncTask";

        @Override
        protected Void doInBackground(final OnSuccessCallback<Feed>... onSuccessCallbacks) {
            OnSuccessCallback<Feed> feedOnSuccessCallback = new OnSuccessCallback<Feed>() {
                @Override
                public void OnSuccess(final Feed feed) {

                    final int size = feed.size();
                    if (!feed.isEmpty()) {
                        final Feed feedList = new Feed();

                        for (final Post post : feed) {
                            Log.d(TAG, "OnSuccess() called with: feedRef = [" + post.getPost_ref() + "]");
                            DocumentReference documentReference = post.getPosted_by();

                            OnSuccessCallback<Profile> callback = new OnSuccessCallback<Profile>() {
                                @Override
                                public void OnSuccess(Profile profile) {
                                    post.setProfile(profile);
                                    if(feedList.contains(post))
                                        feedList.set(feedList.indexOf(post), post);
                                    else
                                        feedList.add(post);

                                    if(feedList.size() == size)
                                        onSuccessCallbacks[0].OnSuccess(feedList);


                                }
                            };

                            Firestore<Profile> firestore = new Firestore<>(documentReference);
                            firestore.retrieve(callback, Profile.class);
                        }
                    } else {
                        onSuccessCallbacks[0].OnSuccess(feed);
                    }
                }
            };
            postFirestore.retrieveList(feedOnSuccessCallback, Post.class, new Feed());
            return null;
        }
    }
}
