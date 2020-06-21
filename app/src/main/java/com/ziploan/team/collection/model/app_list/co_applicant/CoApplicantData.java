package com.ziploan.team.collection.model.app_list.co_applicant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoApplicantData implements Serializable {
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("applicant_type")
    private String mApplicantType;
    @SerializedName("address")
    private String mAddress;

    public CoApplicantData(String mMobile, String mName, String mAddress, String applicantType) {
        this.mMobile = mMobile;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mApplicantType = applicantType;
    }

    public String getApplicantType() {
        return mApplicantType;
    }

    public void setApplicantType(String mApplicantType) {
        this.mApplicantType = mApplicantType;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}
