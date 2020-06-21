package com.ziploan.team.verification_module.caching;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 9/14/2017.
 */

public class FilterKey implements Parcelable{
    @SerializedName("key_name") private String key_name;
    @SerializedName("is_selected") private boolean is_selected;
    @SerializedName("is_filter_selected") private boolean is_filter_selected;

    public FilterKey(String key_name) {
        this.key_name = key_name;
    }

    protected FilterKey(Parcel in) {
        key_name = in.readString();
        is_selected = in.readByte() != 0;
        is_filter_selected = in.readByte() != 0;
    }

    public FilterKey() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key_name);
        dest.writeByte((byte) (is_selected ? 1 : 0));
        dest.writeByte((byte) (is_filter_selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterKey> CREATOR = new Creator<FilterKey>() {
        @Override
        public FilterKey createFromParcel(Parcel in) {
            return new FilterKey(in);
        }

        @Override
        public FilterKey[] newArray(int size) {
            return new FilterKey[size];
        }
    };

    public String getKey_name() {
        return key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }

    public boolean is_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public boolean is_filter_selected() {
        return is_filter_selected;
    }

    public void setIs_filter_selected(boolean is_filter_selected) {
        this.is_filter_selected = is_filter_selected;
    }
}
