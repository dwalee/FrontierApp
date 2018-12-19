package com.frontierapp.frontierapp.listeners;

import com.google.firebase.firestore.DocumentChange;

public interface OnSuccessCallback<S> {

   void OnSuccess(S s);

}
