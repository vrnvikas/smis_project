package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikas Kumar on 24-07-2016.
 */

public class User {

    @SerializedName("token")
    @Expose
    private String token;


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
