package com.ziploan.team.verification_module.borrowerslist;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/3/2017.
 */
public class BorrowersUnverified implements Parcelable{
    @SerializedName("loan_request_status_name") private String loan_request_status_name;
    @SerializedName("business_name") private String business_name;
    @SerializedName("last_name") private String last_name;
    @SerializedName("loan_application_number") private String loan_application_number;
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("mobile") private String mobile;
    @SerializedName("loan_request_assigned_date") private String loan_request_assigned_date;
    @SerializedName("first_name") private String first_name;
    @SerializedName("loan_request_status") private String loan_request_status;
    @SerializedName("business_address") private String business_address;
    @SerializedName("ekyc_status") private int ekyc_status;
    @SerializedName("app_install_status") private int app_install_status;
    @SerializedName("business_pincode") private String business_pincode;
    @SerializedName("business_city") private String business_city;
    @SerializedName("sm_name") private String sm_name;
    @SerializedName("sm_mobile") private String sm_mobile;
    @SerializedName("lender_name") private String lender_name;

    public BorrowersUnverified() {
    }

    protected BorrowersUnverified(Parcel in) {
        loan_request_status_name = in.readString();
        business_name = in.readString();
        last_name = in.readString();
        loan_application_number = in.readString();
        loan_request_id = in.readString();
        mobile = in.readString();
        loan_request_assigned_date = in.readString();
        first_name = in.readString();
        loan_request_status = in.readString();
        business_address = in.readString();
        ekyc_status = in.readInt();
        app_install_status = in.readInt();
        business_pincode = in.readString();
        business_city = in.readString();
        sm_name = in.readString();
        sm_mobile = in.readString();
        lender_name = in.readString();
    }

    public static final Creator<BorrowersUnverified> CREATOR = new Creator<BorrowersUnverified>() {
        @Override
        public BorrowersUnverified createFromParcel(Parcel in) {
            return new BorrowersUnverified(in);
        }

        @Override
        public BorrowersUnverified[] newArray(int size) {
            return new BorrowersUnverified[size];
        }
    };

    public String getLoan_request_status_name() {
        return loan_request_status_name;
    }

    public void setLoan_request_status_name(String loan_request_status_name) {
        this.loan_request_status_name = loan_request_status_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLoan_application_number() {
        return loan_application_number;
    }

    public void setLoan_application_number(String loan_application_number) {
        this.loan_application_number = loan_application_number;
    }

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoan_request_assigned_date() {
        return loan_request_assigned_date;
    }

    public void setLoan_request_assigned_date(String loan_request_assigned_date) {
        this.loan_request_assigned_date = loan_request_assigned_date;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLoan_request_status() {
        return loan_request_status;
    }

    public void setLoan_request_status(String loan_request_status) {
        this.loan_request_status = loan_request_status;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public String getFullAddress(){
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(business_address)){
            stringBuilder.append(business_address);
        }
        if(!TextUtils.isEmpty(business_city)){
            stringBuilder.append(", ");
            stringBuilder.append(business_city);
        }
        if(!TextUtils.isEmpty(business_pincode)){
            stringBuilder.append("-");
            stringBuilder.append(business_pincode);
        }
        return stringBuilder.toString();
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public int getApp_install_status() {
        return app_install_status;
    }

    public void setApp_install_status(int app_install_status) {
        this.app_install_status = app_install_status;
    }

    public String getBusiness_city() {
        return business_city;
    }

    public String getBusiness_pincode() {
        return business_pincode;
    }

    public void setBusiness_city(String business_city) {
        this.business_city = business_city;
    }

    public void setBusiness_pincode(String business_pincode) {
        this.business_pincode = business_pincode;
    }

    public int getEkyc_status() {
        return ekyc_status;
    }

    public void setEkyc_status(int ekyc_status) {
        this.ekyc_status = ekyc_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getLender_name() {
        return lender_name;
    }

    public String getSm_mobile() {
        return sm_mobile;
    }

    public String getSm_name() {
        return sm_name;
    }

    public void setLender_name(String lender_name) {
        this.lender_name = lender_name;
    }

    public void setSm_mobile(String sm_mobile) {
        this.sm_mobile = sm_mobile;
    }

    public void setSm_name(String sm_name) {
        this.sm_name = sm_name;
    }

    public boolean getLenderVisibility(){
        return !TextUtils.isEmpty(lender_name);
    }

    public boolean getSMMobileVisibility(){
        return !TextUtils.isEmpty(sm_mobile);
    }

    public boolean getSmNameVisibility(){
        return !TextUtils.isEmpty(sm_name);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_request_status_name);
        parcel.writeString(business_name);
        parcel.writeString(last_name);
        parcel.writeString(loan_application_number);
        parcel.writeString(loan_request_id);
        parcel.writeString(mobile);
        parcel.writeString(loan_request_assigned_date);
        parcel.writeString(first_name);
        parcel.writeString(loan_request_status);
        parcel.writeString(business_address);
        parcel.writeInt(ekyc_status);
        parcel.writeInt(app_install_status);
        parcel.writeString(business_pincode);
        parcel.writeString(business_city);
        parcel.writeString(sm_mobile);
        parcel.writeString(sm_name);
        parcel.writeString(lender_name);
    }


    public String fetchInstallationStatus(){
        switch (this.app_install_status){
            case -1:
                return "Not Installed";
            case 0:
                return "UnInstalled";
            case 1:
                return "Installed";
        }
        return "Not Installed";
    }
}
