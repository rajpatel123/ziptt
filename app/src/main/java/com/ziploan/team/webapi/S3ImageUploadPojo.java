package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/20/2017.
 */

public class S3ImageUploadPojo implements Parcelable{
    @SerializedName("path") private String path;
    @SerializedName("type") private int type;

    public S3ImageUploadPojo() {
    }

    public S3ImageUploadPojo(String path, int type) {
        this.path = path;
        this.type = type;
    }

    protected S3ImageUploadPojo(Parcel in) {
        path = in.readString();
        type = in.readInt();
    }

    public static final Creator<S3ImageUploadPojo> CREATOR = new Creator<S3ImageUploadPojo>() {
        @Override
        public S3ImageUploadPojo createFromParcel(Parcel in) {
            return new S3ImageUploadPojo(in);
        }

        @Override
        public S3ImageUploadPojo[] newArray(int size) {
            return new S3ImageUploadPojo[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeInt(type);
    }
}
