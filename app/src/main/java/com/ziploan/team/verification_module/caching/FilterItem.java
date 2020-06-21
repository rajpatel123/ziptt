package com.ziploan.team.verification_module.caching;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 9/13/2017.
 */

public class FilterItem implements Parcelable,Comparable<FilterItem>{
    @SerializedName("filter_id") private String filter_id;
    @SerializedName("filter_name") private String filter_name;
    @SerializedName("is_selected") private boolean isSelected;

    protected FilterItem(Parcel in) {
        filter_id = in.readString();
        filter_name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public FilterItem() {

    }

    public FilterItem(String id, String name) {
        this.filter_name = name;
        this.filter_id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filter_id);
        dest.writeString(filter_name);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterItem> CREATOR = new Creator<FilterItem>() {
        @Override
        public FilterItem createFromParcel(Parcel in) {
            return new FilterItem(in);
        }

        @Override
        public FilterItem[] newArray(int size) {
            return new FilterItem[size];
        }
    };

    public String getFilter_id() {
        return filter_id;
    }

    public void setFilter_id(String filter_id) {
        this.filter_id = filter_id;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(@NonNull FilterItem filterItem) {
        return this.filter_id.compareTo(filterItem.filter_id);
    }
}
