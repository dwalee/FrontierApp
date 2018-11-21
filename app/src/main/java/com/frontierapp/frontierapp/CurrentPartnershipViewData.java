package com.frontierapp.frontierapp;

public class CurrentPartnershipViewData {
    String currentPartnerName;
    String currentPartnerProfilePicUrl;
    String currentPartnerId;
    String currentPartnerMessage;


    public CurrentPartnershipViewData(String currentPartnerName, String currentPartnerProfilePicUrl,
                                      String currentPartnerId) {
        this.currentPartnerName = currentPartnerName;
        this.currentPartnerProfilePicUrl = currentPartnerProfilePicUrl;
        this.currentPartnerId = currentPartnerId;
    }

    public CurrentPartnershipViewData() {
    }

    public CurrentPartnershipViewData(String currentPartnerMessage){
        this.currentPartnerMessage = currentPartnerMessage;

    }

    public String getCurrentPartnerId() {
        return currentPartnerId;
    }

    public void setCurrentPartnerId(String currentPartnerId) {
        this.currentPartnerId = currentPartnerId;
    }

    public String getCurrentPartnerName() {
        return currentPartnerName;
    }

    public void setCurrentPartnerName(String currentPartnerName) {
        this.currentPartnerName = currentPartnerName;
    }

    public String getCurrentPartnerProfilePicUrl() {
        return currentPartnerProfilePicUrl;
    }

    public void setCurrentPartnerProfilePicUrl(String currentPartnerProfilePicUrl) {
        this.currentPartnerProfilePicUrl = currentPartnerProfilePicUrl;
    }
    public String getCurrentPartnerMessage() {
        return currentPartnerMessage;
    }

    public void setCurrentPartnerMessage(String currentPartnerMessage) {
        this.currentPartnerMessage = currentPartnerMessage;
    }

}
