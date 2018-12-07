package com.frontierapp.frontierapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostRecyclerAdapter extends FirestoreRecyclerAdapter<Post, PostViewHolder> {
    View view;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PostViewHolder holder, int position, @NonNull Post model) {
        String title = model.getPost_title();
        List<String> image_urls = model.getImage_urls();
        SlideShowAdapter adapter;

        if (title == "")
            holder.postTitleTextView.setVisibility(View.GONE);
        else {
            holder.postTitleTextView.setText(title);
        }
        holder.postDescTextView.setText(model.getPost_desc());
        holder.usernameTextView.setText(model.getPosted_by());
        holder.postTimestampTextView.setText(timeDifference(model.getUpdated()));

        Glide.with(view).load(model.getProfile_url())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profileImageView);

        if(image_urls.isEmpty() || image_urls.get(0) == "")
            holder.mediaPostRelativeLayout.setVisibility(View.GONE);
        else {
            adapter = new SlideShowAdapter(view.getContext(), image_urls);
            holder.postViewPager.setAdapter(adapter);
            if(image_urls.size() > 1)
                holder.circleIndicator.setViewPager(holder.postViewPager);
        }

        int positive_value = (int) model.getPositive_value();
        int negative_value = (int) model.getNegative_value();
        int comment_count = (int) model.getComment_count();

        if(positive_value == negative_value)
            holder.voteValueTextView.setText(0);
        else if(positive_value > negative_value)
            holder.voteValueTextView.setText(Integer.toString(positive_value));
        else if(negative_value > positive_value)
            holder.voteValueTextView.setText(Integer.toString(negative_value));

        holder.commentValueTextView.setText(Integer.toString(comment_count));

        holder.upvoteIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.upvoteIconImageView.setImageResource(R.drawable.upvote_green);
                holder.voteValueTextView.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                holder.downvoteIconImageView.setImageResource(R.drawable.downvote);
            }
        });

        holder.downvoteIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.upvoteIconImageView.setImageResource(R.drawable.upvote);
                holder.downvoteIconImageView.setImageResource(R.drawable.down_vote_red);
                holder.voteValueTextView.setTextColor(Color.RED);
            }
        });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.space_post_item_layout, parent, false);
        return new PostViewHolder(view);
    }

    public String timeDifference(Date newDate){
        long difference = Calendar.getInstance().getTimeInMillis() - newDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long weeksInMilli = daysInMilli * 7;
        double monthsInMilli = weeksInMilli * 4.5;
        long yearsInMilli = weeksInMilli * 52;

        long elapsedYears = difference / yearsInMilli;
        long elapsedMonths = difference / (long) monthsInMilli;
        long elapsedWeeks = difference / weeksInMilli;
        long elapsedDays = difference / daysInMilli;
        long elapsedHours = difference / hoursInMilli;
        long elapsedMinutes = difference / minutesInMilli;
        long elapsedSeconds = difference / secondsInMilli;

        String ago = "ago";
        if(elapsedYears > 0){
            if(elapsedYears > 1)
                return elapsedYears + " years " + ago;
            return elapsedYears + " year " + ago;
        }else if(elapsedMonths > 0){
            if(elapsedMonths > 1 && elapsedMonths != 12)
                return elapsedMonths + " months " + ago;

            return elapsedMonths + " month " + ago;
        }else if(elapsedWeeks > 0){
            if(elapsedWeeks > 1) {
                return elapsedWeeks + " weeks " + ago;
            }
            return elapsedWeeks + " week " + ago;
        }else if(elapsedDays > 0){
            if(elapsedDays > 1)
                return elapsedDays + " days " + ago;
            return elapsedDays + " day " + ago;
        }else if(elapsedHours > 0){
            if(elapsedHours > 1)
                return elapsedHours + " hours " + ago;
            return elapsedHours + " hour " + ago;
        }else if(elapsedMinutes > 0 ){
            if(elapsedMinutes > 1)
                return elapsedMinutes + " minutes " + ago;
            return elapsedMinutes + " minute " + ago;
        }else if(elapsedSeconds > 0){
            if(elapsedSeconds > 1)
                return elapsedSeconds + " seconds " + ago;
            return elapsedSeconds + " second " + ago;
        }

        return "";
    }
}
