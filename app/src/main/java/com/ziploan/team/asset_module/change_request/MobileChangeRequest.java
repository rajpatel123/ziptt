package com.ziploan.team.asset_module.change_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class MobileChangeRequest implements Parcelable {
    @SerializedName("new_mobile_number")
    private String new_mobile_number;
    @SerializedName("confirm_mobile_number")
    private String confirm_mobile_number;
    @SerializedName("loan_request_id")
    private String loan_request_id;
    @SerializedName("request_category")
    private String request_category;
    @SerializedName("proof_url")
    private String proof_url;
    @SerializedName("applicant_type")
    private String mApplicantType;

    public MobileChangeRequest() {

    }

    protected MobileChangeRequest(Parcel in) {
        new_mobile_number = in.readString();
        confirm_mobile_number = in.readString();
        loan_request_id = in.readString();
        request_category = in.readString();
        proof_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(new_mobile_number);
        dest.writeString(confirm_mobile_number);
        dest.writeString(loan_request_id);
        dest.writeString(request_category);
        dest.writeString(proof_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MobileChangeRequest> CREATOR = new Creator<MobileChangeRequest>() {
        @Override
        public MobileChangeRequest createFromParcel(Parcel in) {
            return new MobileChangeRequest(in);
        }

        @Override
        public MobileChangeRequest[] newArray(int size) {
            return new MobileChangeRequest[size];
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

    public String getNew_mobile_number() {
        return new_mobile_number;
    }

    public void setNew_mobile_number(String new_mobile_number) {
        this.new_mobile_number = new_mobile_number;
    }

    public String getConfirm_mobile_number() {
        return confirm_mobile_number;
    }

    public void setConfirm_mobile_number(String confirm_mobile_number) {
        this.confirm_mobile_number = confirm_mobile_number;
    }

    public String getProof_url() {
        return proof_url;
    }

    public void setProof_url(String proof_url) {
        this.proof_url = proof_url;
    }

    public String fetchPhotoFileName() {
        try {
            return new File(this.proof_url).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getmApplicantType() {
        return mApplicantType;
    }

    public void setmApplicantType(String mApplicantType) {
        this.mApplicantType = mApplicantType;
    }
}