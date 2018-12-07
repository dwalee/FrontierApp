package com.frontierapp.frontierapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FeedFragment extends Fragment {
    private final String TAG = "ProjectFragment";
    private View view;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SpaceFirestore spaceFirestore = new SpaceFirestore(getContext());
    private CollectionReference feedRef = spaceFirestore
            .getSpaceCollectionRef("9INlKD4k7wW8HKGWKBtt","Feed");

    private PostRecyclerAdapter postRecyclerAdapter;
    private ImageView spaceBackgroundImageView;

    private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        setUpRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        postRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        postRecyclerAdapter.stopListening();
    }

    public void setUpRecyclerView(View view){
        spaceBackgroundImageView = (ImageView) view.findViewById(R.id.spaceBackgroundImageView);
        Glide.with(view)
                .load("https://usercontent2.hubstatic.com/13719757_f520.jpg")
                .into(spaceBackgroundImageView);
        Query query = feedRef.orderBy("updated", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();

        postRecyclerAdapter = new PostRecyclerAdapter(options);

        RecyclerView postRecyclerview = (RecyclerView) view.findViewById(R.id.feedRecyclerView);
        postRecyclerview.setHasFixedSize(true);
        postRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerview.setAdapter(postRecyclerAdapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
