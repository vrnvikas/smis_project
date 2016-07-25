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

    public static void requestSignUp(String header, SignUpPojo signUpPojo, final Context context) {
        RetroInterface retroInterface = Utility.createRetrofit();
        Call<User> call = retroInterface.registerUser(header, signUpPojo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("token", response.body().getToken() + "");

                Utility.putTokenIn(response.body().getToken(),context);


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });
    }

    public static void statusPostRequest(String header, StatusPostPojo statusBodyPojo, final Context context) {
        RetroInterface retroInterface = Utility.createRetrofit();
        Call<User> call = retroInterface.postUserStatus(header,statusBodyPojo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("vik", response.body().getToken() + "");
                Toast.makeText(context, "status apdated", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
