package com.frontierapp.frontierapp.datasource;

import com.frontierapp.frontierapp.listeners.OnSuccessCallback;

import java.util.ArrayList;

public interface FirestoreReader<T> {
    void retrieve(OnSuccessCallback<T> callback, Class<T> tClass);
    <S extends ArrayList<T>> void retrieveList(OnSuccessCallback<S> callback, Class<T> tClass, S s);
}
