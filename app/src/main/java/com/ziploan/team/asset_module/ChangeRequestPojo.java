package com.ziploan.team.asset_module;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.R;

import java.io.Serializable;

/**
 * Created by ZIploan-Nitesh on 9/8/2017.
 */

public class ChangeRequestPojo implements Serializable, Parcelable{
    @SerializedName("businessAddress") private int businessAddress;
    @SerializedName("primaryAddress") private int primaryAddress;
    @SerializedName("secondaryAddress") private int secondaryAddress;
    @SerializedName("mobile") private int mobile;
    @SerializedName("bankDetails") private int bankDetails;

    public ChangeRequestPojo() {

    }

    public ChangeRequestPojo(Parcel in) {
        businessAddress = in.readInt();
        primaryAddress = in.readInt();
        secondaryAddress = in.readInt();
        mobile = in.readInt();
        bankDetails = in.readInt();
    }

    public static final Creator<ChangeRequestPojo> CREATOR = new Creator<ChangeRequestPojo>() {
        @Override
        public ChangeRequestPojo createFromParcel(Parcel in) {
            return new ChangeRequestPojo(in);
        }

        @Override
        public ChangeRequestPojo[] newArray(int size) {
            return new ChangeRequestPojo[size];
        }
    };

    public int getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(int businessAddress) {
        this.businessAddress = businessAddress;
    }

    public int getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(int primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public int getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(int secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(int bankDetails) {
        this.bankDetails = bankDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(businessAddress);
        parcel.writeInt(primaryAddress);
        parcel.writeInt(secondaryAddress);
        parcel.writeInt(mobile);
        parcel.writeInt(bankDetails);
    }

    public void reset(View view) {
        this.businessAddress = view.getId() == R.id.tv_business_address_change?1-this.businessAddress:0;
        this.primaryAddress = view.getId() == R.id.tv_pri_applicant_add_change?1-this.primaryAddress:0;
        this.secondaryAddress = view.getId() == R.id.tv_sec_applicant_add_change?1-this.secondaryAddress:0;
        this.mobile = view.getId() == R.id.tv_mobile_no?1-this.mobile:0;
        this.bankDetails = view.getId() == R.id.tv_bank_account?1-this.bankDetails:0;
    }

    public void notifyChange() {

    }

    public int getBusinessAddressArrow(){
        return businessAddress==1?R.mipmap.downward:R.mipmap.list_forword;
    }
    public int getPriAddressArrow(){
        return primaryAddress==1?R.mipmap.downward:R.mipmap.list_forword;
    }
    public int getSecAddressArrow(){
        return secondaryAddress==1?R.mipmap.downward:R.mipmap.list_forword;
    }
    public int getMobileArrow(){
        return mobile==1?R.mipmap.downward:R.mipmap.list_forword;
    }
    public int getBankArrow(){
        return bankDetails==1?R.mipmap.downward:R.mipmap.list_forword;
    }
}
