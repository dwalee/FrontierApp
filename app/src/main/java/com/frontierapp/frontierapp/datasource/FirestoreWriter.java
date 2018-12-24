package com.frontierapp.frontierapp.datasource;


import com.google.android.gms.tasks.Task;
import java.util.Map;

public interface FirestoreWriter<T> {
    Task<Void> add(T t);
    Task<Void> add(String id, T t);
    Task<Void> update(String docId, Map<String, Object> map);
    Task<Void> remove(String docId);
}
