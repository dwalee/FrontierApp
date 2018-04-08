package com.frontierapp.frontierapp;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Yoshtown on 3/24/2018.
 */

//Store the data for each post item child view
public class PostItemData {
    private String userName;
    private String postString;
    private String postPhotoUrl;
    private Bitmap postPhoto;
    private Bitmap userAvatarPhoto;
    private ImageDownloader imageDownloader;
    private Date postTimeStamp;
    private String userAvatarUrl;
    private Boolean liked;
    private int likeCount, commentCount, shareCount;
    private int userAvatar;

    public PostItemData(String userName, String postString, String postPhotoUrl,
                        Date postTimeStamp, String userAvatarUrl, Boolean liked,
                        int likeCount, int commentCount, int shareCount, int userAvatar) {
        this.userName = userName;
        this.postString = postString;
        this.postPhotoUrl = postPhotoUrl;
        this.postTimeStamp = postTimeStamp;
        this.userAvatarUrl = userAvatarUrl;
        this.liked = liked;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.userAvatar = userAvatar;
    }

    public PostItemData(String userName, int userAvatar) {
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public Bitmap getPostPhoto() {
        return postPhoto;
    }

    public void setPostPhoto(Bitmap postPhoto) {
        this.postPhoto = postPhoto;
    }

    public Bitmap getUserAvatarPhoto() {
        return userAvatarPhoto;
    }

    public void setUserAvatarPhoto(Bitmap userAvatarPhoto) {
        this.userAvatarPhoto = userAvatarPhoto;
    }

    public PostItemData() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }


    public String getPostString() {
        return postString;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }

    public String getPostPhotoUrl() {
        return postPhotoUrl;
    }

    public void setPostPhotoUrl(String postPhotoUrl) {
        this.postPhotoUrl = postPhotoUrl;
    }

    public Date getPostTimeStamp() {
        return postTimeStamp;
    }

    public void setPostTimeStamp(Date postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    //Download the bitmap image from the given url
    public void convertUserAvatarUrlToBitmap(){
        imageDownloader = new ImageDownloader();
        if(userAvatarUrl != "") {
            try {
                setUserAvatarPhoto(imageDownloader.execute(getUserAvatarUrl()).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    //Download the bitmap image from the given url
    public void convertPostUrlToBitmap() {
        imageDownloader = new ImageDownloader();

        if (postPhotoUrl != "") {
            try {
                setPostPhoto(imageDownloader.execute(getPostPhotoUrl()).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
