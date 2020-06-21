
package com.ziploan.team.collection.model.app_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reference implements Serializable {

    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
