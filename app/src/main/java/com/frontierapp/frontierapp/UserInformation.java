package com.frontierapp.frontierapp;

/**
 * Created by Dwaine on 9/21/17.
 */

public class UserInformation {
    private String nameText, emailText, birthDate, passwordText, Uid, photoUrl,
            Interest1,Interest2,Interest3,Interest4,Interest5,Interest6,Interest7;

    public UserInformation(){

    }


    public UserInformation(String birthDate, String passwordText,String emailText, String nameText, String Uid) {
        this. Uid = Uid;
        this.emailText = emailText;
        this.nameText = nameText;
        this.birthDate = birthDate;
        this.passwordText = passwordText;

    }


    public UserInformation(String Interest1,String Interest2,String Interest3,String Interest4,String Interest5,String Interest6,String Interest7) {
        this.Interest1 = Interest1;
        this.Interest2 = Interest2;
        this.Interest3 = Interest3;
        this.Interest4 = Interest4;
        this.Interest5 = Interest5;
        this.Interest6 = Interest6;
        this.Interest7 = Interest7;

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

    public String getPhotoUrl(){
        return photoUrl;
    }
    public String getInterest1(){
        return Interest1;
    }
    public String getInterest2(){
        return Interest2;
    }
    public String getInterest3(){
        return Interest3;
    }
    public String getInterest4(){
        return Interest4;
    }
    public String getInterest5(){
        return Interest5;
    }
    public String getInterest6(){
        return Interest6;
    }
    public String getInterest7(){
        return Interest7;
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

    public void setInterest1(String Interest1){
        this.Interest1 = Interest1;
    }
    public void setInterest2(String Interest2){
        this.Interest2 = Interest2;
    }
    public void setInterest3(String Interest3){
        this.Interest3 = Interest3;
    }
    public void setInterest4(String Interest4){
        this.Interest4 = Interest4;
    }
    public void setInterest5(String Interest5){
        this.Interest5 = Interest5;
    }
    public void setInterest6(String Interest6){
        this.Interest6 = Interest6;
    }
    public void setInterest7(String Interest7){
        this.Interest7 = Interest7;
    }
}
