package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */
public class SignUpPojo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("fullname")
    @Expose
    private String name;

}
