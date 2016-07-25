package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class FollowPostPojo {

    @SerializedName("user_id")
    @Expose
    private int userId;


    public void setUserID(int id) {
        this.userId = id;
    }


}
