package com.ziploan.team.verification_module.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/10/2017.
 */
public class ZiploanTeamUser {
    @SerializedName("user_type") private String user_type;
    @SerializedName("user_id") private String user_id;

    public ZiploanTeamUser(String user_type, String user_id) {
        this.user_type = user_type;
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
