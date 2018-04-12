package com.frontierapp.frontierapp;

/**
 * Created by Dwaine on 9/21/17.
 */

public class UserInformation {
    private String nameText, emailText, birthDate, passwordText, Uid, photoUrl,gender, skill1, skill2, skill3, skill4, skill5, skill6, skill7;

    public UserInformation(){

    }


    public UserInformation(String nameText, String emailText, String passwordText, String BirthDate, String gender){
        this.Uid = Uid;
        this.emailText = emailText;
        this.nameText = nameText;
        this.birthDate = BirthDate;
        this.passwordText = passwordText;
        this.gender = gender;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.skill5 = skill5;
        this.skill6 = skill6;
        this.skill7 = skill7;

    }



    public String getUid(){
        return Uid;
    }

    public String getName(){
        return nameText;
    }

    public String getEmail(){
        return emailText;
    }

    public String getBirthDate(){
        return birthDate;
    }

    public String getPassword(){
        return passwordText;
    }

    public String getGender(){ return gender;}

    public String getPhotoUrl(){
        return photoUrl;
    }

    public void setName(String nameText){
        this.nameText = nameText;
    }

    public void setEmail(String emailText){
        this.emailText = emailText;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    public void setPasswordText(String passwordText){
        this.passwordText = passwordText;
    }

    public void setUid(String Uid) { this.Uid = Uid;}

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }

}
