package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZIploan-Nitesh on 8/23/2017.
 */

public class FileUploadResponse implements Serializable, Parcelable{
    @SerializedName("url") private String url;
    @SerializedName("msg") private String msg;
    @SerializedName("persisted") private String persisted;
    @SerializedName("updated_id") private String updated_id;
    @SerializedName("persist_error_msg") private String persist_error_msg;

    protected FileUploadResponse(Parcel in) {
        url = in.readString();
        msg = in.readString();
        persisted = in.readString();
        updated_id = in.readString();
        persist_error_msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(msg);
        dest.writeString(persisted);
        dest.writeString(updated_id);
        dest.writeString(persist_error_msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FileUploadResponse> CREATOR = new Creator<FileUploadResponse>() {
        @Override
        public FileUploadResponse createFromParcel(Parcel in) {
            return new FileUploadResponse(in);
        }

        @Override
        public FileUploadResponse[] newArray(int size) {
            return new FileUploadResponse[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPersisted() {
        return persisted;
    }

    public void setPersisted(String persisted) {
        this.persisted = persisted;
    }

    public String getUpdated_id() {
        return updated_id;
    }

    public void setUpdated_id(String updated_id) {
        this.updated_id = updated_id;
    }

    public String getPersist_error_msg() {
        return persist_error_msg;
    }

    public void setPersist_error_msg(String persist_error_msg) {
        this.persist_error_msg = persist_error_msg;
    }
}
