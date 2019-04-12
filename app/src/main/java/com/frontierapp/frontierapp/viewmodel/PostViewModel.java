package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Post;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.repository.PostRepository;
import com.frontierapp.frontierapp.repository.ProfileRepository;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

public class PostViewModel extends ViewModel {
    private ProfileRepository profileRepository = new ProfileRepository();
    private MutableLiveData<Profile> profileMutableLiveData;

    private PostRepository postRepository = new PostRepository();
    private MutableLiveData<Post> postMutableLiveData;

    public void update(DocumentReference profileDocRef, Map<String, Object> postData){
        postRepository.update(profileDocRef, postData);
    }

    public void retrieveProfile(DocumentReference profileDocumentReference){
        profileRepository.retrieveProfile(profileDocumentReference);
        profileMutableLiveData = profileRepository.getProfile();
    }

    public void retrievePost(DocumentReference postDocumentReference){
        postRepository.retrievePost(postDocumentReference);
        postMutableLiveData = postRepository.getPost();
    }

    public MutableLiveData<Profile> getProfile() {
        return profileMutableLiveData;
    }

    public MutableLiveData<Post> getPost() {
        return postMutableLiveData;
    }
}
