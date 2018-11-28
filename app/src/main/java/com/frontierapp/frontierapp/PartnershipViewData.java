package com.frontierapp.frontierapp;

import android.graphics.Bitmap;

import java.util.concurrent.ExecutionException;

/**
 * Created by Yoshtown on 3/29/2018.
 */

public class PartnershipViewData {
    String first_name;
    String last_name;
    String profile_url;

    public PartnershipViewData() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }
}
