package com.frontierapp.frontierapp;

import android.graphics.Bitmap;

import java.util.concurrent.ExecutionException;

/**
 * Created by Yoshtown on 3/29/2018.
 */

public class PartnershipViewData {
    String partnerName;
    String partnerAvatarUrl;
    Bitmap partnerAvatarBitmap;
    ImageDownloader imageDownloader;

    public Bitmap getPartnerAvatarBitmap() {
        return partnerAvatarBitmap;
    }

    public void setPartnerAvatarBitmap(Bitmap partnerAvatarBitmap) {
        this.partnerAvatarBitmap = partnerAvatarBitmap;
    }

    public PartnershipViewData(String partnerName, String partnerAvatarUrl) {
        this.partnerName = partnerName;
        this.partnerAvatarUrl = partnerAvatarUrl;
    }

    public PartnershipViewData() {
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerAvatarUrl() {
        return partnerAvatarUrl;
    }

    public void setPartnerAvatarUrl(String partnerAvatarUrl) {
        this.partnerAvatarUrl = partnerAvatarUrl;
    }

    //Download the bitmap image from the given url
    public void convertUserAvatarUrlToBitmap(){
        imageDownloader = new ImageDownloader();
        if(getPartnerAvatarUrl() != "") {
            try {
                setPartnerAvatarBitmap(imageDownloader.execute(getPartnerAvatarUrl()).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
