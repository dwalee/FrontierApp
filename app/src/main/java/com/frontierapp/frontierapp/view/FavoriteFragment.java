package com.frontierapp.frontierapp.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.frontierapp.frontierapp.adapter.ConnectionsRecyclerViewAdapter;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.databinding.FragmentConnectionBinding;
import com.frontierapp.frontierapp.model.Connections;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.util.SpacesItemDecoration;
import com.frontierapp.frontierapp.viewmodel.FavoriteViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragment";
    FragmentConnectionBinding fragmentConnectionBinding;
    RecyclerView connectionRecyclerView;
    private final CollectionReference collectionReference =  FirestoreDBReference.userCollection
            .document(Firestore.currentUserId)
            .collection(FirestoreConstants.CONNECTIONS);
    private FavoriteViewModel connectionsViewModel;
    Context context;

    private final Query query = collectionReference.whereEqualTo(FirestoreConstants.FAVORITE, true);
    private ConnectionsRecyclerViewAdapter currentConnectionItemRecyclerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentConnectionBinding =
                FragmentConnectionBinding.inflate(inflater, container, false);
        init();

        return fragmentConnectionBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        connectionsViewModel = ViewModelProviders.of(getActivity()).get(FavoriteViewModel.class);
        getConnections();
        connectionsViewModel.getConnections().observe(getViewLifecycleOwner(), new Observer<Connections>() {
            @Override
            public void onChanged(@Nullable Connections connections) {
                currentConnectionItemRecyclerAdapter.submitList(connections);
            }
        });


    }

    public void getConnections(){
        connectionsViewModel.retrieveConnections(query);
    }

    public void init(){

        connectionRecyclerView = fragmentConnectionBinding.connectionRecyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        connectionRecyclerView.setLayoutManager(gridLayoutManager);
        connectionRecyclerView.setHasFixedSize(true);
        currentConnectionItemRecyclerAdapter = new ConnectionsRecyclerViewAdapter(getLayoutInflater(), getActivity());
        int spacing = 5;
        boolean includeEdge = false;

        connectionRecyclerView.addItemDecoration(new SpacesItemDecoration(2, spacing, includeEdge));
        connectionRecyclerView.setAdapter(currentConnectionItemRecyclerAdapter);

    }

}
