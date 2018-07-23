package com.frontierapp.frontierapp;

public interface PartnerReader {
    Profile getPartner(String partnerId);
    Profiles getPartners();
    Boolean findPartner(String partnerId);
}
