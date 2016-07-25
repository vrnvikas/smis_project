package com.onthespot.vikaskumar.miniproject;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class UserStausModel {

    private static String userName;
    private static String status;
    private static String email;
    private static String token;

    public static String getToken() {
        return token;
    }

    public static String getEmail() {
        return email;
    }

    public static String getStatus() {
        return status;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserStausModel.userName = userName;
    }

    public static void setStatus(String status) {
        UserStausModel.status = status;
    }

    public static void setEmail(String email) {
        UserStausModel.email = email;
    }

    public static void setToken(String token) {
        UserStausModel.token = token;
    }
}
