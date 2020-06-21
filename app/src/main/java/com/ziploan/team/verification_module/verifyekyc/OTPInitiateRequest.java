package com.ziploan.team.verification_module.verifyekyc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/10/2017.
 */

public class OTPInitiateRequest implements Parcelable{
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("applicant_type") private String applicant_type;
    @SerializedName("aadhar_no") private String aadhar_no;

    public OTPInitiateRequest(String loan_request_id, String applicant_type, String aadhar_no) {
        this.loan_request_id = loan_request_id;
        this.applicant_type = applicant_type;
        this.aadhar_no = aadhar_no;
    }

    protected OTPInitiateRequest(Parcel in) {
        loan_request_id = in.readString();
        applicant_type = in.readString();
        aadhar_no = in.readString();
    }

    public static final Creator<OTPInitiateRequest> CREATOR = new Creator<OTPInitiateRequest>() {
        @Override
        public OTPInitiateRequest createFromParcel(Parcel in) {
            return new OTPInitiateRequest(in);
        }

        @Override
        public OTPInitiateRequest[] newArray(int size) {
            return new OTPInitiateRequest[size];
        }
    };

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getApplicant_type() {
        return applicant_type;
    }

    public void setApplicant_type(String applicant_type) {
        this.applicant_type = applicant_type;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_request_id);
        parcel.writeString(applicant_type);
        parcel.writeString(aadhar_no);
    }
}
