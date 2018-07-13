package com.frontierapp.frontierapp;

public class FavoriteViewData {
    String favoriteName;
    String favoriteProfilePicUrl;
    String favoriteId;

    public FavoriteViewData() {
    }

    public FavoriteViewData(String favoriteName, String favoriteProfilePicUrl, String favoriteId) {
        this.favoriteName = favoriteName;
        this.favoriteProfilePicUrl = favoriteProfilePicUrl;
        this.favoriteId = favoriteId;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public String getFavoriteProfilePicUrl() {
        return favoriteProfilePicUrl;
    }

    public void setFavoriteProfilePicUrl(String favoriteProfilePicUrl) {
        this.favoriteProfilePicUrl = favoriteProfilePicUrl;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }
}
