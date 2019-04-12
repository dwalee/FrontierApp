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
import com.frontierapp.frontierapp.model.Voter;
import com.frontierapp.frontierapp.model.Voters;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class FeedRepository  implements OnSuccessCallback<Feed> {
    private static final String TAG = "FeedRepository";
    private MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
    private Firestore<Post> postFirestore;
    private PostAsyncTask postAsyncTask;

    public void retrieveFeed(Query query) {
        postAsyncTask = new PostAsyncTask();
        postFirestore = new Firestore(query);
        postAsyncTask.execute(this);
    }

    public MutableLiveData<Feed> getFeed() {
        return feedMutableLiveData;
    }

    @Override
    public void OnSuccess(Feed feed) {
        Log.i(TAG, "Voter upvote = " + feed.get(0).isUpvote());
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

                                    OnSuccessCallback<Voter> voterOnSuccessCallback = new OnSuccessCallback<Voter>() {
                                        @Override
                                        public void OnSuccess(Voter voter) {

                                            if (voter != null) {
                                                Log.i(TAG, "Voter upvote = " + voter.isUp_vote());
                                                post.setDownvote(voter.isDown_vote());
                                                post.setUpvote(voter.isUp_vote());

                                                OnSuccessCallback<Voters> upVoteCountCallback = new OnSuccessCallback<Voters>() {
                                                    @Override
                                                    public void OnSuccess(Voters upVoters) {
                                                        if(upVoters != null)
                                                            post.setPositive_count(upVoters.size());
                                                        else
                                                            post.setPositive_count(0);
                                                    }
                                                };

                                                Query q = post.getPost_ref().collection("Voters").whereEqualTo("up_vote", true);
                                                Firestore<Voter> upVoterCountFirestore = new Firestore<>(q);
                                                upVoterCountFirestore.retrieveList(upVoteCountCallback, Voter.class, new Voters());

                                                OnSuccessCallback<Voters> downVoteCountCallback = new OnSuccessCallback<Voters>() {
                                                    @Override
                                                    public void OnSuccess(Voters upVoters) {
                                                        if(upVoters != null)
                                                            post.setNegative_count(upVoters.size());
                                                        else
                                                            post.setNegative_count(0);
                                                    }
                                                };

                                                Query q2 = post.getPost_ref().collection("Voters").whereEqualTo("down_vote", true);
                                                Firestore<Voter> downVoterCountFirestore = new Firestore<>(q2);
                                                downVoterCountFirestore.retrieveList(downVoteCountCallback, Voter.class, new Voters());
                                            }

                                            if(feedList.size() == size)
                                                onSuccessCallbacks[0].OnSuccess(feedList);
                                        }
                                    };

                                    DocumentReference reference = post.getPost_ref().collection("Voters").document(Firestore.currentUserId);

                                    Firestore<Voter> voterFirestore = new Firestore<>(reference);
                                    voterFirestore.retrieve(voterOnSuccessCallback, Voter.class);



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
