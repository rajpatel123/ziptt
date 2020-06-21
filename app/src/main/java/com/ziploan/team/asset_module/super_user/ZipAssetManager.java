package com.ziploan.team.asset_module.super_user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;

/**
 * Created by ZIploan-Nitesh on 9/15/2017.
 */

public class ZipAssetManager implements Parcelable{
    @SerializedName("manager_id") private String manager_id;
    @SerializedName("manager_name") private String manager_name;

    protected ZipAssetManager(Parcel in) {
        manager_id = in.readString();
        manager_name = in.readString();
    }

    public ZipAssetManager(String key, String s) {
        manager_id = key;
        manager_name = s;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(manager_id);
        dest.writeString(manager_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ZipAssetManager> CREATOR = new Creator<ZipAssetManager>() {
        @Override
        public ZipAssetManager createFromParcel(Parcel in) {
            return new ZipAssetManager(in);
        }

        @Override
        public ZipAssetManager[] newArray(int size) {
            return new ZipAssetManager[size];
        }
    };

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public String fetchAssetManagerName(){
        return ZiploanUtil.keyToText(manager_name);
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }
}
