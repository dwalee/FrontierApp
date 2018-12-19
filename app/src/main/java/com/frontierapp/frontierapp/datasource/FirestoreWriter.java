package com.frontierapp.frontierapp.datasource;

import java.util.HashMap;

public interface FirestoreWriter<T> {
    void add(T t);
    void add(String id,T t);
    void update(String docId, HashMap<String, Object> map);
    void remove(String docId);
}
