package com.ziploan.team.asset_module.change_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class BusinessAddressChangeRequest implements Parcelable {
    @SerializedName("loan_request_id")
    private String loan_request_id;
    @SerializedName("request_category")
    private String request_category;
    @SerializedName("business_address")
    private String business_address;
    @SerializedName("business_city")
    private String business_city;
    @SerializedName("business_state")
    private String business_state;
    @SerializedName("business_pincode")
    private String business_pincode;
    @SerializedName("proof_url")
    private String proof_url;


    protected BusinessAddressChangeRequest(Parcel in) {
        loan_request_id = in.readString();
        request_category = in.readString();
        business_address = in.readString();
        business_city = in.readString();
        business_state = in.readString();
        business_pincode = in.readString();
        proof_url = in.readString();
    }

    public BusinessAddressChangeRequest() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loan_request_id);
        dest.writeString(request_category);
        dest.writeString(business_address);
        dest.writeString(business_city);
        dest.writeString(business_state);
        dest.writeString(business_pincode);
        dest.writeString(proof_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusinessAddressChangeRequest> CREATOR = new Creator<BusinessAddressChangeRequest>() {
        @Override
        public BusinessAddressChangeRequest createFromParcel(Parcel in) {
            return new BusinessAddressChangeRequest(in);
        }

        @Override
        public BusinessAddressChangeRequest[] newArray(int size) {
            return new BusinessAddressChangeRequest[size];
        }
    };

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getBusiness_city() {
        return business_city;
    }

    public void setBusiness_city(String business_city) {
        this.business_city = business_city;
    }

    public String getBusiness_state() {
        return business_state;
    }

    public void setBusiness_state(String business_state) {
        this.business_state = business_state;
    }

    public String getBusiness_pincode() {
        return business_pincode;
    }

    public void setBusiness_pincode(String business_pincode) {
        this.business_pincode = business_pincode;
    }

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
}