package com.onthespot.vikaskumar.miniproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class RequestUtility {


    public static void requestSignUp(String header, SignUpPojo signUpPojo, final Context context, RequestResponse response) {
        RetroInterface retroInterface = Utility.createRetrofit();
        Call<UserTokenModel> call = retroInterface.registerUser(header, signUpPojo);
        final RequestResponse requestResponse = response;
        call.enqueue(new Callback<UserTokenModel>() {
            @Override
            public void onResponse(Call<UserTokenModel> call, Response<UserTokenModel> response) {
                if (response.body() != null) {
                    Log.i("token", response.body().getToken() + "");
                    Utility.putTokenIn(response.body().getToken(), context);
                    requestResponse.onSuccess();

                } else {
                    Toast.makeText(context, "ServerError", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserTokenModel> call, Throwable t) {

                requestResponse.onError();
            }
        });
    }

    public static void statusPostRequest(String header, StatusPostPojo statusBodyPojo, final Context context) {
        RetroInterface retroInterface = Utility.createRetrofit();
        Call<UserTokenModel> call = retroInterface.postUserStatus(header, statusBodyPojo);

        call.enqueue(new Callback<UserTokenModel>() {
            @Override
            public void onResponse(Call<UserTokenModel> call, Response<UserTokenModel> response) {
                Log.i("vik", response.body().getToken() + "");
                Toast.makeText(context, "status apdated", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserTokenModel> call, Throwable t) {

            }
        });
    }

    public static void followRequest(String header, FollowPostPojo followPostPojo, final Context context) {

        RetroInterface retroInterface = Utility.createRetrofit();
        Call<UserFollowStatusPojo> call = retroInterface.postUserFollowRequest(header, followPostPojo);

        call.enqueue(new Callback<UserFollowStatusPojo>() {
            @Override
            public void onResponse(Call<UserFollowStatusPojo> call, Response<UserFollowStatusPojo> response) {

                if(response.body() != null&& response.body().getStatus() == "true"){
                    Toast.makeText(context, "status apdated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserFollowStatusPojo> call, Throwable t) {

            }
        });

    }
}
