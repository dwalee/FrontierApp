package com.frontierapp.frontierapp.view;

public class Upload {
    private String imageUrl;
    private String uid;

    public Upload(){

    }

    public Upload(String imageUrl){

    }

    public Upload(String uid, String imageUrl){
            this.imageUrl = imageUrl;
            this.uid = uid;

        }


        public String getImageUrl() {
            return imageUrl;

        }


        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
                    this.uid = uid;
                }

            }


