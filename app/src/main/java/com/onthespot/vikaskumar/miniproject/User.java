package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class User {

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
