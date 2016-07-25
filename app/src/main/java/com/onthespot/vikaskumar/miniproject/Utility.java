package com.onthespot.vikaskumar.miniproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vikas Kumar on 24-07-2016.
 */

public class Utility {

    public static RetroInterface createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(URL.BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetroInterface.class);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static String getHashString(String message, String algorithm) {

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte arrayByte : arrayBytes) {
            stringBuilder.append(Integer.toString((arrayByte & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuilder.toString();
    }

    public static void parseDatabase(Response<List<StatusBodyPojo>> response, Context context) {
        UserStatusDataBase dataBase = UserStatusDataBase.newInstance(context);
        dataBase.deleteDatabase();
        for(StatusBodyPojo body:response.body()){
            dataBase.insertRow("vikas",body.getPostContent(),"23","email");
        }

    }

    public static void putTokenIn(String token,Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SharedPrefConstants.APP_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static boolean UserTokenExists(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SharedPrefConstants.APP_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        String token = prefs.getString("token",SharedPrefConstants.DEFAULT);

        return !token.equals("default");

    }

    public static void clearToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SharedPrefConstants.APP_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token",SharedPrefConstants.DEFAULT);
        editor.commit();
    }
}
