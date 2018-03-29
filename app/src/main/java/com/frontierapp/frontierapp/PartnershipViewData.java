package com.frontierapp.frontierapp;

/**
 * Created by Yoshtown on 3/29/2018.
 */

public class PartnershipViewData {
    String partnerName;
    int partnerAvatar;

    public PartnershipViewData(String partnerName, int partnerAvatar) {
        this.partnerName = partnerName;
        this.partnerAvatar = partnerAvatar;
    }

    public PartnershipViewData() {
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getPartnerAvatar() {
        return partnerAvatar;
    }

    public void setPartnerAvatar(int partnerAvatar) {
        this.partnerAvatar = partnerAvatar;
    }
}
