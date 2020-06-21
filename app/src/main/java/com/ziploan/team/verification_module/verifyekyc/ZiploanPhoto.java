package com.ziploan.team.verification_module.verifyekyc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.utils.AppConstant;

/**
 * Created by ZIploan-Nitesh on 2/7/2017.
 */
public class ZiploanPhoto implements Parcelable {
    @SerializedName("photoPath")
    private String photoPath;
    @SerializedName("remote_path")
    private String remote_path;
    @SerializedName("upload_status")
    private int upload_status = -1;
    @SerializedName("photoIdentifier")
    private String photoIdentifier;
    @SerializedName("media_type")
    private String media_type;

    public ZiploanPhoto(String photoPath) {
        this.photoPath = photoPath;
        this.media_type = AppConstant.MediaType.IMAGE;
    }

    public ZiploanPhoto(String photoPath, String media_type) {
        this.photoPath = photoPath;
        this.media_type = media_type;
    }

    protected ZiploanPhoto(Parcel in) {
        photoPath = in.readString();
        remote_path = in.readString();
        upload_status = in.readInt();
        photoIdentifier = in.readString();
        media_type = in.readString();
    }

    public static final Creator<ZiploanPhoto> CREATOR = new Creator<ZiploanPhoto>() {
        @Override
        public ZiploanPhoto createFromParcel(Parcel in) {
            return new ZiploanPhoto(in);
        }

        @Override
        public ZiploanPhoto[] newArray(int size) {
            return new ZiploanPhoto[size];
        }
    };

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getRemote_path() {
        return remote_path;
    }

    public void setRemote_path(String remote_path) {
        this.remote_path = remote_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photoPath);
        parcel.writeString(remote_path);
        parcel.writeInt(upload_status);
        parcel.writeString(photoIdentifier);
        parcel.writeString(media_type);
    }

    public String getPhotoIdentifier() {
        return photoIdentifier;
    }

    public void setPhotoIdentifier(String photoIdentifier) {
        this.photoIdentifier = photoIdentifier;
    }

    public int getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(int upload_status) {
        this.upload_status = upload_status;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }
}
