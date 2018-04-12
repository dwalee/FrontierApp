package com.frontierapp.frontierapp;

public class NavHeaderData {
    String firstName;
    String LastName;
    int profilePic;



    public void NavHeaderData(){

    }

    public  void NavHeaderData(String firstName, String lastName, int profilePic){
        this.firstName = firstName;
        this.LastName = LastName;
        this.profilePic = profilePic;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
