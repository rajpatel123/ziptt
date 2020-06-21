
package com.ziploan.team.collection.model.record_visit;

import com.google.gson.annotations.SerializedName;

public class RecordVisitRequestModel {

    @SerializedName("amount")
    private int mAmount;
    @SerializedName("bank_name")
    private String mBankName;
    @SerializedName("comment")
    private String mComment;
    @SerializedName("denominations")
    private Denominations mDenominations;
    @SerializedName("is_person_met")
    private Boolean mIsPersonMet;
    @SerializedName("mode")
    private Integer mMode;
    @SerializedName("person_met")
    private String mPersonMet;
    @SerializedName("reference_number")
    private String mRefrenceNumber;
    @SerializedName("value_date")
    private String mValueDate;
    @SerializedName("file_url")
    private String mFileUrl;
    @SerializedName("identifier")
    private String mIdentifier;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("place")
    private String place;
    @SerializedName("ptp_status")
    private String ptpStatus;
    @SerializedName("lat") private double lat;
    @SerializedName("lon") private double lng;

    public Integer getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Denominations getDenominations() {
        return mDenominations;
    }

    public void setDenominations(Denominations denominations) {
        mDenominations = denominations;
    }

    public Boolean getIsPersonMet() {
        return mIsPersonMet;
    }

    public void setIsPersonMet(Boolean isPersonMet) {
        mIsPersonMet = isPersonMet;
    }

    public Integer getMode() {
        return mMode;
    }

    public void setMode(Integer mode) {
        mMode = mode;
    }

    public String getPersonMet() {
        return mPersonMet;
    }

    public void setPersonMet(String personMet) {
        mPersonMet = personMet;
    }

    public String getRefrenceNumber() {
        return mRefrenceNumber;
    }

    public void setRefrenceNumber(String refrenceNumber) {
        mRefrenceNumber = refrenceNumber;
    }

    public String getValueDate() {
        return mValueDate;
    }

    public void setValueDate(String valueDate) {
        mValueDate = valueDate;
    }

    public String getmFileUrl() {
        return mFileUrl;
    }

    public void setmFileUrl(String mFileUrl) {
        this.mFileUrl = mFileUrl;
    }

    public String getmIdentifier() {
        return mIdentifier;
    }

    public void setmIdentifier(String mIdentifier) {
        this.mIdentifier = mIdentifier;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPtpStatus() {
        return ptpStatus;
    }

    public void setPtpStatus(String ptpStatus) {
        this.ptpStatus = ptpStatus;
    }
}
