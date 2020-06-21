
package com.ziploan.team.collection.model.app_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicantInfo {

    @SerializedName("residential_pincode")
    @Expose
    private String residentialPincode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("residential_address")
    @Expose
    private String residentialAddress;
    @SerializedName("residential_state")
    @Expose
    private String residentialState;
    @SerializedName("residential_city")
    @Expose
    private String residentialCity;
    @SerializedName("applicant_name")
    @Expose
    private String applicantName;

    public String getResidentialPincode() {
        return residentialPincode;
    }

    public void setResidentialPincode(String residentialPincode) {
        this.residentialPincode = residentialPincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getResidentialState() {
        return residentialState;
    }

    public void setResidentialState(String residentialState) {
        this.residentialState = residentialState;
    }

    public String getResidentialCity() {
        return residentialCity;
    }

    public void setResidentialCity(String residentialCity) {
        this.residentialCity = residentialCity;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

}
