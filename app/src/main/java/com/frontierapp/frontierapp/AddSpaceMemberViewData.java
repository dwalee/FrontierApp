package com.frontierapp.frontierapp;

public class AddSpaceMemberViewData {
    private String member_url;
    private String member_name;
    private Boolean isChecked;
    private String member_id;

    public String getMember_url() {
        return member_url;
    }

    public void setMember_url(String member_url) {
        this.member_url = member_url;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
