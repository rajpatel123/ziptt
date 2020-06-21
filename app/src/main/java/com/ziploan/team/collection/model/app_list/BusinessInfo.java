
package com.ziploan.team.collection.model.app_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessInfo {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("business_pincode")
    @Expose
    private String businessPincode;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_city")
    @Expose
    private String businessCity;
    @SerializedName("business_state")
    @Expose
    private String businessState;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPincode() {
        return businessPincode;
    }

    public void setBusinessPincode(String businessPincode) {
        this.businessPincode = businessPincode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessState() {
        return businessState;
    }

    public void setBusinessState(String businessState) {
        this.businessState = businessState;
    }

}
