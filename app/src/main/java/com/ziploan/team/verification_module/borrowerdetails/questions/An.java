
package com.ziploan.team.verification_module.borrowerdetails.questions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class An implements Parcelable {

    @SerializedName("val")
    @Expose
    private Val val;
    @SerializedName("key")
    @Expose
    private String key;

    public An(){}

    protected An(Parcel in) {
        val = in.readParcelable(Val.class.getClassLoader());
        key = in.readString();
    }

    public static final Creator<An> CREATOR = new Creator<An>() {
        @Override
        public An createFromParcel(Parcel in) {
            return new An(in);
        }

        @Override
        public An[] newArray(int size) {
            return new An[size];
        }
    };

    public Val getVal() {
        return val;
    }

    public void setVal(Val val) {
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(val, i);
        parcel.writeString(key);
    }
}
