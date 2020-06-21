package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/10/2017.
 */

public class LoginRequest {
    @SerializedName("email") String user_email;
    @SerializedName("password") String user_password;
    @SerializedName("user_type_id") String user_type_id;

    public LoginRequest(String user_email, String user_password, String user_type_id) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_type_id = user_type_id;
    }
}
