package com.frontierapp.frontierapp;

public enum PartnerStatus {
    True("True"),
    False("False"),
    Pending_Response("Pending Response"),
    Pending_Sent("Pending Sent");


    PartnerStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
