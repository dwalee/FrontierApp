package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FavoriteItemRecyclerAdapter extends FirestoreRecyclerAdapter<ConnectionsViewDataModel, ConnectionViewHolder> {
    private static final String TAG = "FavoriteRecycler";
    private View view;
    private final String CURRENT_CONNECTIONS_ID = "current_connections_id";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FavoriteItemRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ConnectionsViewDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ConnectionViewHolder holder, int position, @NonNull ConnectionsViewDataModel model) {
        Glide.with(view.getContext()).load(model.getProfile_url())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.connectionProfileImageView);

        holder.connectionNameTextView.setText(model.getFirst_name() + " " + model.getLast_name());
        holder.connectionRequest.setVisibility(View.GONE);

        holder.connectionTitleTextView.setText(model.getTitle());

        final String id = getSnapshots().getSnapshot(position).getReference().getId();
        holder.connectionItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connectionsProfileIntent = new Intent(view.getContext(), CurrentConnectionsProfileActivity.class);
                //partnerProfileIntent.putExtra("CurrentPartnerIdIndex", position);
                connectionsProfileIntent.putExtra(CURRENT_CONNECTIONS_ID,
                        id);
                Log.i(TAG, "onClick: connections_id = " + id);
                view.getContext().startActivity(connectionsProfileIntent);
            }
        });
    }

    @NonNull
    @Override
    public ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connections_item_layout,
                parent, false);

        return new ConnectionViewHolder(view);
    }

}
