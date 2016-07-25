package com.onthespot.vikaskumar.miniproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */
public class UserListBodyPojo {


    @SerializedName("following")
    @Expose
    private List<Integer> following = new ArrayList<Integer>();
    @SerializedName("users")
    @Expose
    private List<User> users = new ArrayList<>();

    public List<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Integer> following) {
        this.following = following;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
