package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by USER on 24-04-2018.
 */

public class BusinessCategory implements Serializable {
    @SerializedName("business_category_name")
    private String business_category_name;

    @SerializedName("business_category_id")
    private String business_category_id;

    public String getBusiness_category_name() {
        return business_category_name;
    }

    public void setBusiness_category_name(String business_category_name) {
        this.business_category_name = business_category_name;
    }

    public String getBusiness_category_id() {
        return business_category_id;
    }

    public void setBusiness_category_id(String business_category_id) {
        this.business_category_id = business_category_id;
    }
}
