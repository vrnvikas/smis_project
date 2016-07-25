package com.onthespot.vikaskumar.miniproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Vikas Kumar on 24-07-2016.
 */

public interface RetroInterface {

    @GET(URL.LOGIN)
    Call<User> loginUser();

    @POST(URL.SIGN_UP)
    Call<User> registerUser(@Header("Authorization") String authoriazation, @Body SignUpPojo body);

    @POST(URL.POST_STATUS)
    Call<User> postUserStatus(@Header("Authorization") String header, @Body StatusPostPojo statusBodyPojo);

    @GET(URL.USER_STATUS)
    Call<List<StatusBodyPojo>> getStatus(@Header("Authorization") String header);

    Call<StausPojo> requestUserStatus(String header, StatusBodyPojo statusBodyPojo);
}
