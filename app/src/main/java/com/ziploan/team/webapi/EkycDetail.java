package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/18/2017.
 */
public class EkycDetail implements Parcelable{
    @SerializedName("status") private String status;
    @SerializedName("aadhar_number") private String aadhar_number;
    @SerializedName("applicant_name") private String applicant_name;
    @SerializedName("applicant_type") private String applicant_type;

    protected EkycDetail(Parcel in) {
        status = in.readString();
        aadhar_number = in.readString();
        applicant_name = in.readString();
        applicant_type = in.readString();
    }

    public static final Creator<EkycDetail> CREATOR = new Creator<EkycDetail>() {
        @Override
        public EkycDetail createFromParcel(Parcel in) {
            return new EkycDetail(in);
        }

        @Override
        public EkycDetail[] newArray(int size) {
            return new EkycDetail[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAadhar_number() {
        return aadhar_number;
    }

    public void setAadhar_number(String aadhar_number) {
        this.aadhar_number = aadhar_number;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_type() {
        return applicant_type;
    }

    public void setApplicant_type(String applicant_type) {
        this.applicant_type = applicant_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(aadhar_number);
        parcel.writeString(applicant_name);
        parcel.writeString(applicant_type);
    }
}
