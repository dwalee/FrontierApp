package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Post;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

public class PostRepository implements OnSuccessCallback<Post> {
    private MutableLiveData<Post> postMutableLiveData = new MutableLiveData<>();
    private Firestore postFirestore;
    private PostAsyncTask postAsyncTask;
    private Firestore<Post> myPostFirestore;

    public void update(DocumentReference postRef, final Map<String, Object> postData){
        postRef.update(postData);
    }

    public void retrievePost(DocumentReference postDocumentReference){
        postAsyncTask = new PostAsyncTask();
        postFirestore = new Firestore(postDocumentReference);
        postAsyncTask.execute(this);
    }

    public MutableLiveData<Post> getPost() {
        return postMutableLiveData;
    }

    @Override
    public void OnSuccess(Post post) {
        postMutableLiveData.setValue(post);
    }

    public class PostAsyncTask extends AsyncTask<OnSuccessCallback<Post>, Void, Void> {

        @Override
        protected Void doInBackground(OnSuccessCallback<Post>... onSuccessCallbacks) {
            postFirestore.retrieve(onSuccessCallbacks[0], Post.class);
            return null;
        }
    }


}
