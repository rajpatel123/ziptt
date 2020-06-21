package com.ziploan.team.asset_module.change_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class ResidenceAddressChangeRequest implements Parcelable {
    @SerializedName("residence_address")
    private String residence_address;
    @SerializedName("residence_city")
    private String residence_city;
    @SerializedName("residence_state")
    private String residence_state;
    @SerializedName("residence_pincode")
    private String residence_pincode;
    @SerializedName("proof_url")
    private String proof_url;
    @SerializedName("loan_request_id")
    private String loan_request_id;
    @SerializedName("request_category")
    private String request_category;
    @SerializedName("applicant_type")
    private String mApplicantType;

    protected ResidenceAddressChangeRequest(Parcel in) {
        residence_address = in.readString();
        residence_city = in.readString();
        residence_state = in.readString();
        residence_pincode = in.readString();
        proof_url = in.readString();
        loan_request_id = in.readString();
        request_category = in.readString();
    }

    public ResidenceAddressChangeRequest() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(residence_address);
        dest.writeString(residence_city);
        dest.writeString(residence_state);
        dest.writeString(residence_pincode);
        dest.writeString(proof_url);
        dest.writeString(loan_request_id);
        dest.writeString(request_category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResidenceAddressChangeRequest> CREATOR = new Creator<ResidenceAddressChangeRequest>() {
        @Override
        public ResidenceAddressChangeRequest createFromParcel(Parcel in) {
            return new ResidenceAddressChangeRequest(in);
        }

        @Override
        public ResidenceAddressChangeRequest[] newArray(int size) {
            return new ResidenceAddressChangeRequest[size];
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

    public String getResidence_address() {
        return residence_address;
    }

    public void setResidence_address(String residence_address) {
        this.residence_address = residence_address;
    }

    public String getResidence_city() {
        return residence_city;
    }

    public void setResidence_city(String residence_city) {
        this.residence_city = residence_city;
    }

    public String getResidence_state() {
        return residence_state;
    }

    public void setResidence_state(String residence_state) {
        this.residence_state = residence_state;
    }

    public String getResidence_pincode() {
        return residence_pincode;
    }

    public void setResidence_pincode(String residence_pincode) {
        this.residence_pincode = residence_pincode;
    }

    public String getProof_url() {
        return proof_url;
    }

    public String fetchPhotoFileName() {
        try {
            return new File(this.proof_url).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setProof_url(String proof_url) {
        this.proof_url = proof_url;
    }

    public String getmApplicantType() {
        return mApplicantType;
    }

    public void setmApplicantType(String mApplicantType) {
        this.mApplicantType = mApplicantType;
    }
}