package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */
public class StatusPostPojo {

    @SerializedName("post_content")
    @Expose
    private String status;


    public void setStatus(String status) {
        this.status = status;
    }
}
