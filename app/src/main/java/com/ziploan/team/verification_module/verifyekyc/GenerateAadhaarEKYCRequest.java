package com.ziploan.team.verification_module.verifyekyc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/10/2017.
 */

public class GenerateAadhaarEKYCRequest implements Parcelable{
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("applicant_type") private String applicant_type;
    @SerializedName("authorization_key") private String authorization_key;
    @SerializedName("otp") private String otp;

    public GenerateAadhaarEKYCRequest(String loan_request_id, String applicant_type, String authorization_key, String otp) {
        this.loan_request_id = loan_request_id;
        this.applicant_type = applicant_type;
        this.authorization_key = authorization_key;
        this.otp = otp;
    }

    protected GenerateAadhaarEKYCRequest(Parcel in) {
        loan_request_id = in.readString();
        applicant_type = in.readString();
        authorization_key = in.readString();
        otp = in.readString();
    }

    public static final Creator<GenerateAadhaarEKYCRequest> CREATOR = new Creator<GenerateAadhaarEKYCRequest>() {
        @Override
        public GenerateAadhaarEKYCRequest createFromParcel(Parcel in) {
            return new GenerateAadhaarEKYCRequest(in);
        }

        @Override
        public GenerateAadhaarEKYCRequest[] newArray(int size) {
            return new GenerateAadhaarEKYCRequest[size];
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

    public String getAuthorization_key() {
        return authorization_key;
    }

    public void setAuthorization_key(String authorization_key) {
        this.authorization_key = authorization_key;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_request_id);
        parcel.writeString(applicant_type);
        parcel.writeString(authorization_key);
        parcel.writeString(otp);
    }
}
