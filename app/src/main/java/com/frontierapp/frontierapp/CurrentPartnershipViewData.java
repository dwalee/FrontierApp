package com.frontierapp.frontierapp;

import android.graphics.Bitmap;

public class CurrentPartnershipViewData {
    String currentPartnerName;
    String currentPartnerAvatarUrl;

    public CurrentPartnershipViewData(String currentPartnerName, String currentPartnerAvatarUrl) {
        this.currentPartnerName = currentPartnerName;
        this.currentPartnerAvatarUrl = currentPartnerAvatarUrl;
    }

    public CurrentPartnershipViewData() {
    }

    public String getCurrentPartnerName() {
        return currentPartnerName;
    }

    public void setCurrentPartnerName(String currentPartnerName) {
        this.currentPartnerName = currentPartnerName;
    }

    public String getCurrentPartnerAvatarUrl() {
        return currentPartnerAvatarUrl;
    }

    public void setCurrentPartnerAvatarUrl(String currentPartnerAvatarUrl) {
        this.currentPartnerAvatarUrl = currentPartnerAvatarUrl;
    }

}
