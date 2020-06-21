package com.ziploan.team.asset_module.change_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 9/13/2017.
 */

public class TopUpRequest implements Parcelable {
    @SerializedName("loan_request_id")
    private String loan_request_id;
    @SerializedName("request_category")
    private String request_category;

    public TopUpRequest() {
    }

    protected TopUpRequest(Parcel in) {
        loan_request_id = in.readString();
        request_category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loan_request_id);
        dest.writeString(request_category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopUpRequest> CREATOR = new Creator<TopUpRequest>() {
        @Override
        public TopUpRequest createFromParcel(Parcel in) {
            return new TopUpRequest(in);
        }

        @Override
        public TopUpRequest[] newArray(int size) {
            return new TopUpRequest[size];
        }
    };

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getRequest_category() {
        return request_category;
    }

    public void setRequest_category(String request_category) {
        this.request_category = request_category;
    }
}
