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

    @POST(URL.SIGN_UP)
    Call<UserTokenModel> registerUser(@Header("Authorization") String authoriazation, @Body SignUpPojo body);

    @POST(URL.POST_STATUS)
    Call<UserTokenModel> postUserStatus(@Header("Authorization") String header, @Body StatusPostPojo statusBodyPojo);

    @GET(URL.USER_STATUS)
    Call<List<StatusBodyPojo>> getStatus(@Header("Authorization") String header);

    @GET(URL.LOGIN)
    Call<UserTokenModel> LoginUserUser(@Header("Authorization") String header);

    @GET(URL.USER_LIST)
    Call<UserListBodyPojo> getUserList(@Header("Authorization") String header);

    @POST(URL.USER_LIST)
    Call<UserFollowStatusPojo> postUserFollowRequest(@Header("Authorization") String header,@Body FollowPostPojo followPostPojo);
}
